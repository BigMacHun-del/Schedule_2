package sparta.schedule2.Comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.schedule2.Comment.dto.*;
import sparta.schedule2.Comment.service.CommentService;
import sparta.schedule2.User.dto.SessionUser;
import sparta.schedule2.exception.SesstionNotFoundException;

import java.util.List;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.saveComment(scheduleId, sessionUser,request));
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<GetCommentsResponse>> getAllComment(
            @PathVariable Long scheduleId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComment(scheduleId));
    }

    @GetMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<GetCommentResponse> getOneComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getOneComment(scheduleId, commentId));
    }

    @PutMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser

    ){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(scheduleId, commentId, sessionUser.getUserId(),request));
    }

    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ){
        commentService.deleteComment(scheduleId, commentId, sessionUser.getUserId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
