package sparta.schedule2.Schedule.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.schedule2.Schedule.dto.*;
import sparta.schedule2.Schedule.entity.Schedule;
import sparta.schedule2.Schedule.repository.ScheduleRepository;
import sparta.schedule2.User.entity.User;
import sparta.schedule2.User.repository.UserRepository;
import sparta.schedule2.exception.NoAccessException;
import sparta.schedule2.exception.ScheduleNotFountException;
import sparta.schedule2.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponse saveSchedule(Long userId, CreateScheduleRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );

        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), user);
        Schedule saveSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(saveSchedule);
    }

    @Transactional(readOnly = true)
    public List<GetSchedulesResponse> getAllSchedule() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(GetSchedulesResponse::new) //(schedule -> new GetSchedulesResponse(schedule)) 여기서 자동 변경됨
                .toList();
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse getOneSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFountException("없는 일정입니다.")
        );
        return new GetScheduleResponse(schedule);
    }

    @Transactional
    public @Nullable UpdateScheduleResposne updateSchedule(Long scheduleId, Long loginUserId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFountException("없는 일정입니다.")
        );

        if (!schedule.getUser().getUserId().equals(loginUserId)){  //작성자 Id와 로그인 한 Id가 같지 않으면 수정 불가
            throw new NoAccessException("작성자 본인만 수정할 수 있습니다.");
        }

        schedule.update(request.getTitle(), request.getContent());
        return new UpdateScheduleResposne(schedule);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId, Long loginUserId) {
//        boolean existence = scheduleRepository.existsById(scheduleId);
//        if(!existence){
//            throw new ScheduleNotFountException("없는 일정입니다.");
//        }
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFountException("없는 일정입니다.")
        );

        if (!schedule.getUser().getUserId().equals(loginUserId)){
            throw new NoAccessException("작성자 본인만 삭제할 수 있습니다.");
        }

        scheduleRepository.deleteById(scheduleId);



    }
}
