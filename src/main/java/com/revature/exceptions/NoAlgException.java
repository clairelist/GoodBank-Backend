package com.revature.exceptions;

public class NoAlgException extends RuntimeException {

    public NoAlgException() {
    }

    public NoAlgException(String message) {
        super(message);
    }

    public NoAlgException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAlgException(Throwable cause) {
        super(cause);
    }

    public NoAlgException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}