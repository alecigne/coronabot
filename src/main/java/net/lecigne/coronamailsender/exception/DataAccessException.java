package net.lecigne.coronamailsender.exception;

public class DataAccessException extends RuntimeException {

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
