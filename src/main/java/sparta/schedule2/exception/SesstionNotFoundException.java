package sparta.schedule2.exception;

import org.springframework.http.HttpStatus;

public class SesstionNotFoundException extends ServerException{
    public SesstionNotFoundException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
