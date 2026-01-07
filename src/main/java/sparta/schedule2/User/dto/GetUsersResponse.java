package sparta.schedule2.User.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUsersResponse {
    private final Long userId;
    private final String userName;
    private final String email;


    public GetUsersResponse(Long userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }
}
