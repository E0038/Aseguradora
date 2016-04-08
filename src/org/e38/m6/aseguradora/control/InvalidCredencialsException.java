package org.e38.m6.aseguradora.control;

/**
 * Created by sergi on 4/7/16.
 */
public class InvalidCredencialsException extends Exception {
    public InvalidCredencialsException() {
    }

    public InvalidCredencialsException(String message) {
        super(message);
    }

    public InvalidCredencialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCredencialsException(Throwable cause) {
        super(cause);
    }

    public InvalidCredencialsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
