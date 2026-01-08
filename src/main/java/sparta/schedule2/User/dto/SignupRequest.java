package sparta.schedule2.User.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequest {
    @NotBlank(message = "유저 이름은 필수값입니다.")
    private String userName;

    @NotBlank(message = "이메일은 필수값입니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;
}
