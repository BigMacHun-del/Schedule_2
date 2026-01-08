package sparta.schedule2.Comment.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.schedule2.Comment.dto.CreateCommentRequest;
import sparta.schedule2.Comment.dto.CreateCommentResponse;
import sparta.schedule2.Comment.entity.Comment;
import sparta.schedule2.Comment.repository.CommentRepository;
import sparta.schedule2.Schedule.entity.Schedule;
import sparta.schedule2.Schedule.repository.ScheduleRepository;
import sparta.schedule2.User.dto.SessionUser;
import sparta.schedule2.User.entity.User;
import sparta.schedule2.User.repository.UserRepository;
import sparta.schedule2.exception.ScheduleNotFountException;
import sparta.schedule2.exception.UserNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public @Nullable CreateCommentResponse save(Long scheduleId, SessionUser sessionUser, @Valid CreateCommentRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFountException("없는 일정입니다.")
        );
        User user = userRepository.findById(sessionUser.getUserId())
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다."));

        Comment comment = new Comment(request.getContent(), schedule, user);  //댓글을 쓴 일정과 작성자 명 정보 저장
        Comment saveComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                saveComment.getCommentId(),
                schedule.getScheduleId(),
                user.getUserId(),
                saveComment.getContent(),
                user.getUserName(),  //작성자 명
                saveComment.getCommentCreateAt(),
                saveComment.getCommentUpdateAt()
        );
    }
}
