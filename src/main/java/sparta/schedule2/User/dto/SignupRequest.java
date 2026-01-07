package sparta.schedule2.User.dto;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String userName;
    private String email;
    private String password;
}
