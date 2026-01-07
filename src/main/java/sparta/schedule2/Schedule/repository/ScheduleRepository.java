package sparta.schedule2.Schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.schedule2.Schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
