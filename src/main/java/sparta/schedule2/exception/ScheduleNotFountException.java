package sparta.schedule2.exception;

import org.springframework.http.HttpStatus;

public class ScheduleNotFountException extends ServerException{
    public ScheduleNotFountException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
