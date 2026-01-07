package sparta.schedule2.Schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.schedule2.Schedule.dto.*;
import sparta.schedule2.Schedule.service.ScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/users/{userId}/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @PathVariable Long userId,
            @RequestBody CreateScheduleRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.saveSchedule(userId, request));
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<GetSchedulesResponse>> getAllSchedule(){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAllSchedule());
    }

    @GetMapping("/schedules/{scheduleID}")
    public ResponseEntity<GetScheduleResponse> getOneSchedule(
            @PathVariable Long scheduleID
    ){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getOneSchedule(scheduleID));
    }

    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResposne> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(scheduleId, request));
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId
    ){
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
