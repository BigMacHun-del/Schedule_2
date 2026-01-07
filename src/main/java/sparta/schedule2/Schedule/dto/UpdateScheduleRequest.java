package sparta.schedule2.Schedule.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequest {
    private String title;
    private String content;
    private String email;
    private String password;
}
