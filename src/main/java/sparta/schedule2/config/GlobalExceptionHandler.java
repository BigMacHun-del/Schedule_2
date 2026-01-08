package sparta.schedule2.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sparta.schedule2.exception.ServerException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // UserNotFoundException 커스텀 에러 핸들링
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<String> handleServerException(ServerException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.getMessage());
    }
}
