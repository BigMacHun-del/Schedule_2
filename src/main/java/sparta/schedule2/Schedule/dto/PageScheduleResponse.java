package sparta.schedule2.Schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PageScheduleResponse {
    private final Long scheduleId;
    private final String title;
    private final String content;
    private final Long commentCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;
    private final String userName;


    public PageScheduleResponse(Long scheduleId, String title, String content, Long commentCount, LocalDateTime createdAt, LocalDateTime updatedAt, String userName) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userName = userName;
    }
}
