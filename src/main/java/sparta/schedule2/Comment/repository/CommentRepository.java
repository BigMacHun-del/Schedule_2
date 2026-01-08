package sparta.schedule2.Comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.schedule2.Comment.entity.Comment;
import sparta.schedule2.Schedule.entity.Schedule;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySchedule(Schedule schedule);
}
