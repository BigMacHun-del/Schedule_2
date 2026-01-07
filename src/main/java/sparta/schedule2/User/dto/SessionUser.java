package sparta.schedule2.User.dto;

import lombok.Getter;

@Getter
public class SessionUser {
    private final Long userId;
    private final String email;
    private final String userName;

    public SessionUser(Long userId, String email, String userName) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
    }
}
