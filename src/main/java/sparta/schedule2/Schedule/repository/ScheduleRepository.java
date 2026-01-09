package sparta.schedule2.Schedule.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparta.schedule2.Schedule.dto.PageScheduleResponse;
import sparta.schedule2.Schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    //TODO: JPQL 분석 필요(그냥 따라 쳤음)
    @Query("""
    select new sparta.schedule2.Schedule.dto.PageScheduleResponse(
        s.scheduleId,
        s.title,
        s.content,
        count(c),
        s.scheduleCreateAt,
        s.scheduleUpdateAt,
        u.userName
    )
    from Schedule s
    join s.user u
    left join Comment c on c.schedule = s
    group by
        s.scheduleId,
        s.title,
        s.content,
        s.scheduleCreateAt,
        s.scheduleUpdateAt,
        u.userName
""")
    Page<PageScheduleResponse> findAllWithPaging(Pageable pageable);
}
