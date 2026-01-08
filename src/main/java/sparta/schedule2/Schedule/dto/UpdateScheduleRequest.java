package sparta.schedule2.Schedule.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {
    private String title;
    private String content;

    @Email(message = "이메일 형식으로 작성하세요.")
    private String email;
    private String password;
}
