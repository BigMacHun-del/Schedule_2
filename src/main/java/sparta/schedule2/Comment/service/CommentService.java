package sparta.schedule2.Comment.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.schedule2.Comment.dto.*;
import sparta.schedule2.Comment.entity.Comment;
import sparta.schedule2.Comment.repository.CommentRepository;
import sparta.schedule2.Schedule.entity.Schedule;
import sparta.schedule2.Schedule.repository.ScheduleRepository;
import sparta.schedule2.User.dto.SessionUser;
import sparta.schedule2.User.entity.User;
import sparta.schedule2.User.repository.UserRepository;
import sparta.schedule2.exception.CommentNotFoundException;
import sparta.schedule2.exception.NoAccessException;
import sparta.schedule2.exception.ScheduleNotFoundException;
import sparta.schedule2.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public @Nullable CreateCommentResponse saveComment(Long scheduleId, SessionUser sessionUser, @Valid CreateCommentRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );
        User user = userRepository.findById(sessionUser.getUserId())
                .orElseThrow(() -> new UserNotFoundException("로그인이 필요합니다."));

        Comment comment = new Comment(request.getContent(), schedule, user);  //댓글을 쓴 일정과 작성자 명 정보 저장
        Comment saveComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                saveComment.getCommentId(),
                schedule.getScheduleId(),
                user.getUserId(),
                user.getUserName(),  //작성자 명
                saveComment.getContent(),
                saveComment.getCommentCreateAt(),
                saveComment.getCommentUpdateAt()
        );
    }

    @Transactional(readOnly = true)
    public @Nullable List<GetCommentsResponse> getAllComment(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );

        List<Comment> comments = commentRepository.findBySchedule(schedule);
        return comments.stream()
                .map(comment -> new GetCommentsResponse(
                        comment.getCommentId(),
        //TODO: 아래와 같은 방식을 사용하면 트래픽이 적을때는 상관없겠지만, 많아지면 서버 과부하 발생 가능성 생김 -> DTO에서 Projection 사용해서 보완할 것.
                        comment.getSchedule().getScheduleId(),
                        comment.getUser().getUserId(),
                        comment.getUser().getUserName(),
                        comment.getContent()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public @Nullable GetCommentResponse getOneComment(Long scheduleId, Long commentId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("없는 댓글입니다.")
        );
        return new GetCommentResponse(
                comment.getCommentId(),
                comment.getSchedule().getScheduleId(),
                comment.getUser().getUserId(),
                comment.getUser().getUserName(),
                comment.getContent(),
                comment.getCommentCreateAt(),
                comment.getCommentUpdateAt()
        );
    }

    @Transactional
    public @Nullable UpdateCommentResponse updateComment(Long scheduleId, Long commentId, Long loginUserId, UpdateCommentRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("없는 댓글입니다.")
        );

        if (!comment.getUser().getUserId().equals(loginUserId)){  //작성자 Id와 로그인 한 Id가 같지 않으면 수정 불가
            throw new NoAccessException("작성자 본인만 수정할 수 있습니다.");
        }

        ///여기서는 일정 수정 기능과는 다르게 2차검증까진 안함.

        comment.update(request.getContent());
        return new UpdateCommentResponse(
                comment.getCommentId(),
                comment.getSchedule().getScheduleId(),
                comment.getUser().getUserId(),
                comment.getUser().getUserName(),
                comment.getContent(),
                comment.getCommentCreateAt(),
                comment.getCommentUpdateAt()
        );
    }

    @Transactional
    public void deleteComment(Long scheduleId, Long commentId, Long loginUserId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("없는 댓글입니다.")
        );

        if (!schedule.getUser().getUserId().equals(loginUserId)){
            throw new NoAccessException("작성자 본인만 삭제할 수 있습니다.");
        }

        commentRepository.deleteById(commentId);

    }

}
