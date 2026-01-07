package sparta.schedule2.User.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.schedule2.User.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
