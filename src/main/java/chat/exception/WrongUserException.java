package chat.exception;

import org.springframework.http.HttpStatus;

public class WrongUserException extends RuntimeException {
    private HttpStatus status;

    public WrongUserException(final String message) {
        super(message);
    }

    public WrongUserException(final Throwable cause) {
        super(cause);
    }

    public WrongUserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public WrongUserException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
