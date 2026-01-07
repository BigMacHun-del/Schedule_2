package sparta.schedule2.User.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateUserResponse {
    private final Long userId;
    private final String userName;
    private final String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")  //시간 데이터 포멧 변경
    private final LocalDateTime userCreateAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime userUpdateAt;

    public UpdateUserResponse(Long userId, String userName, String email, LocalDateTime userCreateAt, LocalDateTime userUpdateAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.userCreateAt = userCreateAt;
        this.userUpdateAt = userUpdateAt;
    }
}
