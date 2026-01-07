package sparta.schedule2.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.schedule2.User.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
