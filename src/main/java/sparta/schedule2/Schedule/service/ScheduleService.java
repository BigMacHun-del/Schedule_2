package sparta.schedule2.Schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.schedule2.Schedule.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
}
