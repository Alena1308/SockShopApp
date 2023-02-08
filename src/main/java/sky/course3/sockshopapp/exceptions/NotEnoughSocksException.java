package sky.course3.sockshopapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotEnoughSocksException extends RuntimeException{
    public NotEnoughSocksException() {
        super("Not enough socks.");
    }

    public NotEnoughSocksException(String message) {
        super(message);
    }

    public NotEnoughSocksException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughSocksException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughSocksException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
