package sparta.schedule2.Schedule.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sparta.schedule2.User.entity.User;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)  //Auditing 이용하여 작성일 수정일 활성화
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    //유저가 있어야 일정 작성 가능하므로 널 불가
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(length = 30, nullable = false)
    @NotBlank(message = "일정 제목은 필수값입니다.")
    private String title;

    @Column(length = 200)
    private String content;

    @CreatedDate
    private LocalDateTime scheduleCreateAt;

    @LastModifiedDate
    private LocalDateTime scheduleUpdateAt;

    public Schedule( String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
