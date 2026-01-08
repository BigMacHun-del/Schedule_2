package sparta.schedule2.Comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.schedule2.Comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
