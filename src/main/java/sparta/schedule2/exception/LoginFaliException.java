package sparta.schedule2.exception;

import org.springframework.http.HttpStatus;

public class LoginFaliException extends ServerException{
    public LoginFaliException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
