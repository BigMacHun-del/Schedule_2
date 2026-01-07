package sparta.schedule2.Schedule.dto;

import lombok.Getter;
import sparta.schedule2.User.entity.User;

@Getter
public class UserResponse {  // 일정에서는 유저명과 유저 아이디를 동시에 받아와야 하기 때문에 유저를 가져오는 클래스
    private final Long userId;
    private final String userName;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
    }
}
