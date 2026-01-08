package sparta.schedule2.Comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.schedule2.Comment.dto.CreateCommentRequest;
import sparta.schedule2.Comment.dto.CreateCommentResponse;
import sparta.schedule2.Comment.service.CommentService;
import sparta.schedule2.User.dto.SessionUser;
import sparta.schedule2.exception.SesstionNotFoundException;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComent(
            @Valid @RequestBody CreateCommentRequest request,
            @PathVariable Long scheduleId,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ){
        if (sessionUser == null){
            throw new SesstionNotFoundException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(scheduleId, sessionUser,request));
    }
}
