package sparta.schedule2.User.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.schedule2.User.dto.*;
import sparta.schedule2.User.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
            @RequestBody SignupRequest request
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        SessionUser sessionUser = userService.login(request);
        session.setAttribute("loginUser", sessionUser);  //set-cookie를 통해 서버가 클라이언트에게 JsessionId를 보내준다
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, HttpSession session) {
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }

        session.invalidate(); //세션 무효화 시켜 로그아웃 상태로 만듦
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //유저 전체 검색
    @GetMapping("/users")
    public ResponseEntity<List<GetUsersResponse>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }


    //유저 단 건 검색
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getOneUser(
            @PathVariable Long userId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getOneUser(userId));
    }

    //유저 정보 수정(자기 자신만 수정 할 수 있음)
    //SessionAttribute 적용하여, 로그인 상태 적용
    @PutMapping("/users")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @RequestBody UpdateUserRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(sessionUser.getUserId(), request));
    }

    //유저 정보 삭제(자기 자신만 삭제 할 수 있음)
    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, HttpSession session
    ){
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  //세션 유저가 없다면 권한 없음
        }
        userService.delete(sessionUser.getUserId());
        session.invalidate();  //세션 로그아웃

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
