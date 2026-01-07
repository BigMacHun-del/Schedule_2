package sparta.schedule2.User.controller;

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

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(
            @RequestBody CreateUserRequest request
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUsersResponse>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getOneUser(
            @PathVariable Long userId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getOneUser(userId));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @RequestBody UpdateUserRequest request,
            @PathVariable Long userId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, request));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId
    ){
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
