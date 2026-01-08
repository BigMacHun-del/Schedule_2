package sparta.schedule2.User.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.schedule2.User.dto.*;
import sparta.schedule2.User.entity.User;
import sparta.schedule2.User.repository.UserRepository;
import sparta.schedule2.config.PasswordEncoder;
import sparta.schedule2.exception.LoginFaliException;
import sparta.schedule2.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponse save(SignupRequest request) {
        String encodePassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getUserName(), request.getEmail(), encodePassword);
        User saveUser = userRepository.save(user);
        return new SignupResponse(
                saveUser.getUserId(),
                saveUser.getUserName(),
                saveUser.getEmail(),
                saveUser.getUserCreateAt(),
                saveUser.getUserUpdateAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetUsersResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        List<GetUsersResponse> dtos = new ArrayList<>();
        for (User user : users) {
            GetUsersResponse dto = new GetUsersResponse(
                    user.getUserId(),
                    user.getUserName(),
                    user.getEmail()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public GetUserResponse getOneUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );
        return new GetUserResponse(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getUserCreateAt(),
                user.getUserUpdateAt()
        );
    }

    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );
        user.update(request.getUserName(),request.getEmail());
        return new UpdateUserResponse(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getUserCreateAt(),
                user.getUserUpdateAt()
        );
    }

    @Transactional
    public void delete(Long userId) {
        boolean existence = userRepository.existsById(userId);
        if (!existence){
            throw new UserNotFoundException("없는 유저입니다.");
        }

        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public SessionUser login(@Valid LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new LoginFaliException("이메일 또는 비밀번호가 틀렸습니다.")
        );

        boolean matches = passwordEncoder.matches(
                request.getPassword(), //request로 받은 비밀번호(평문)
                user.getPassword() //기존에 저장되어 있던 암호화된 비밀번호(암호문)
        );
        if (!matches){
            throw new LoginFaliException("이메일 또는 비밀번호가 틀렸습니다.");
        }

        return new SessionUser(
                user.getUserId(),
                user.getEmail(),
                user.getUserName()
        );
    }
}
