package sparta.schedule2.Comment.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    private String content;
}
