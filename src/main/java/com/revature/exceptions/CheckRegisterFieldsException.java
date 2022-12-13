package com.revature.exceptions;

public class CheckRegisterFieldsException extends RuntimeException{
    public CheckRegisterFieldsException() {
    }

    public CheckRegisterFieldsException(String message) {
        super(message);
    }

    public CheckRegisterFieldsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckRegisterFieldsException(Throwable cause) {
        super(cause);
    }

    public CheckRegisterFieldsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
