package sparta.schedule2.User.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserResponse {

    private final Long userId;
    private final String userName;
    private final String email;
    private final LocalDateTime userCreateAt;
    private final LocalDateTime userUpdateAt;

    public GetUserResponse(Long userId, String userName, String email, LocalDateTime userCreateAt, LocalDateTime userUpdateAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.userCreateAt = userCreateAt;
        this.userUpdateAt = userUpdateAt;
    }
}
