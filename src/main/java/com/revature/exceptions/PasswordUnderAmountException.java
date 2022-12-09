package com.revature.exceptions;

public class PasswordUnderAmountException extends RuntimeException{
    public PasswordUnderAmountException() {
    }

    public PasswordUnderAmountException(String message) {
        super(message);
    }

    public PasswordUnderAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordUnderAmountException(Throwable cause) {
        super(cause);
    }

    public PasswordUnderAmountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
