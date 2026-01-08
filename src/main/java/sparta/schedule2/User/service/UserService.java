package sparta.schedule2.User.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.schedule2.User.dto.*;
import sparta.schedule2.User.entity.User;
import sparta.schedule2.User.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public SignupResponse save(SignupRequest request) {
        User user = new User(request.getUserName(), request.getEmail(), request.getPassword());
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
                () -> new IllegalArgumentException("없는 유저입니다.")
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
                () -> new IllegalArgumentException("없는 유저입니다.")
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
            throw new IllegalArgumentException("없는 유저입니다.");
        }

        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public SessionUser login(@Valid LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("없는 유저입니다.")
        );

        return new SessionUser(
                user.getUserId(),
                user.getEmail(),
                user.getUserName()
        );
    }
}
