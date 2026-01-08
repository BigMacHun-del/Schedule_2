package sparta.schedule2.Schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    @NotBlank(message = "일정 제목은 필수값입니다.")
    private String title;

    @Size(min = 10, max = 1000, message = "10~1000자 사이로 적어주세요.")  //10~1000 사이즈 설정
    @NotBlank(message = "초기 일정 내용은 필수값입니다.")
    private String content;
}
