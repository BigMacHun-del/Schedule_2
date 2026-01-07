package sparta.schedule2.Schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import sparta.schedule2.Schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResposne {
    private final Long scheduleId;
    private final UserResponse user; //User entity를 직접 넣으면 3 Layer 아키텍처 위반함.
    private final String title;
    private final String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime scheduleCreateAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime scheduleUpdateAt;

    public UpdateScheduleResposne(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.user = new UserResponse(schedule.getUser());
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.scheduleCreateAt = schedule.getScheduleCreateAt();
        this.scheduleUpdateAt = schedule.getScheduleUpdateAt();
    }
}
