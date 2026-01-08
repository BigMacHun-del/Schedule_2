package sparta.schedule2.Comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {
    private final Long commentId;
    private final Long scheduleId;
    private final Long userId;
    private final String userName;  //댓글을 쓴 작성자 명
    private final String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime commentCreateAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime commentUpdateAt;

    public CreateCommentResponse(Long commentId, Long scheduleId, Long userId, String content, String userName, LocalDateTime commentCreateAt, LocalDateTime commentUpdateAt) {
        this.commentId = commentId;
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.content = content;
        this.userName = userName;
        this.commentCreateAt = commentCreateAt;
        this.commentUpdateAt = commentUpdateAt;
    }
}
