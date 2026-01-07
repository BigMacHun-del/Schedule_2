package sparta.schedule2.User.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import sparta.schedule2.User.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}
