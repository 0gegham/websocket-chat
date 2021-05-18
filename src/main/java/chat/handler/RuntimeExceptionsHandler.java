package chat.handler;

import chat.exception.WrongUserException;
import chat.models.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RuntimeExceptionsHandler {
    @ExceptionHandler(value = WrongUserException.class)
    public ExceptionResponse handleTheWrongUserException(WrongUserException exception) {
        return ExceptionResponse.builder()
                .message(exception.getMessage())
                .status(exception.getStatus().value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ExceptionResponse handleOtherRuntimeExceptions(RuntimeException exception) {
        return ExceptionResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
