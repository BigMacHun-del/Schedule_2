package sparta.schedule2.Comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentsResponse {
    private final Long commentId;
    private final Long scheduleId;
    private final Long userId;
    private final String userName;  //댓글을 쓴 작성자 명
    private final String content;

    public GetCommentsResponse(Long commentId, Long scheduleId,  Long userId, String userName, String content) {
        this.commentId = commentId;
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
    }
}
