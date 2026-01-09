package sparta.schedule2.Schedule.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.schedule2.Schedule.dto.*;
import sparta.schedule2.Schedule.service.ScheduleService;
import sparta.schedule2.User.dto.SessionUser;
import sparta.schedule2.exception.SesstionNotFoundException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    //일정 생성(로그인한 유저의 일정 생성)
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @Valid @RequestBody CreateScheduleRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ){
        if(sessionUser == null){
            throw new SesstionNotFoundException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.saveSchedule(sessionUser.getUserId(), request));
    }

    //일정 전체 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<GetSchedulesResponse>> getAllSchedule(){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAllSchedule());
    }

    //일정 단건 조회
    @GetMapping("/schedules/{scheduleID}")
    public ResponseEntity<GetScheduleResponse> getOneSchedule(
            @PathVariable Long scheduleID
    ){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOneSchedule(scheduleID));
    }

    //일정 수정(해당 일정 작성자와 지금 로그인 한 세션이 같을 때 수정 권한 부여)
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResposne> updateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(scheduleId, sessionUser.getUserId(), request));
    }

    //일정 삭제(해당 일정 작성자와 지금 로그인 한 세션이 같을 때 삭제 권한 부여)
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ){
        scheduleService.deleteSchedule(scheduleId, sessionUser.getUserId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //일정 전체 조회(페이징)
    @GetMapping("/schedules/pages")
    //TODO: Response가 많이 나오는 이유: content필드에 PageScheduleResponse가 묶여서 나오고 나머지는 Page 객체의 메타데이터
    public ResponseEntity<Page<PageScheduleResponse>> getSchedules(
            @RequestParam(defaultValue = "1") int page,  //페이지 번호
            @RequestParam(defaultValue = "10") int size  //페이지 크기
    ) {
        int pageIndex = page - 1;  //클라이언트에겐 페이지 1로 보이고 서버 내부는 service 코드에서 0이다.
        return ResponseEntity.ok(scheduleService.getSchedules(pageIndex, size));
    }

}
