package sparta.schedule2.Comment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sparta.schedule2.Schedule.entity.Schedule;
import sparta.schedule2.User.entity.User;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)  //Auditing 이용하여 작성일 수정일 활성화
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CommentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "scheduleId", nullable = false)
    private Schedule schedule;

    @Column(length = 200)
    private String content;

    @CreatedDate
    private LocalDateTime commentCreateAt;

    @LastModifiedDate
    private LocalDateTime commentUpdateAt;

    //작성자 명을 알기 위해서 User 받아줌
    public Comment(String content, Schedule schedule, User user){
        this.content = content;
        this.schedule = schedule;
        this.user = user;
    };

    public void update(String content) {
        this.content = content;
    }
}
