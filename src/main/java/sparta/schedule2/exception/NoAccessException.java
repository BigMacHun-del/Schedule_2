package sparta.schedule2.exception;

import org.springframework.http.HttpStatus;

public class NoAccessException extends ServerException{
    public NoAccessException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
