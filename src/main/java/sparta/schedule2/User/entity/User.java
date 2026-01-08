package sparta.schedule2.User.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)  //Auditing 이용하여 작성일 수정일 활성화
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 20, nullable = false)
    @NotBlank(message = "유저 이름은 필수값입니다.")
    private String userName;

    //validation은 dto에서 추가적으로 선언 -> 특히 Email이나 Size 같은 어노테이션
    @Column(length = 100, nullable = false, unique = true)  //이메일은 단일값 지정
    @NotBlank(message = "이메일은 필수값입니다.")
    private String email;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "비밀번호는 필수값입니다.")
    private String password;

    @CreatedDate
    @Column(updatable = false) //최초 값을 넣고 update 불가 -> 작성일 보장
    private LocalDateTime userCreateAt;

    @LastModifiedDate
    private LocalDateTime userUpdateAt;

    public User(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void update(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}
