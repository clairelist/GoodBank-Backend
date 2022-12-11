package com.revature.exceptions;

public class UserNotAllowedException extends RuntimeException{
    public UserNotAllowedException() {}

    public UserNotAllowedException(String message){
        super(message);
    }

    public UserNotAllowedException(Throwable cause){
        super(cause);
    }

    public UserNotAllowedException(String message, Throwable cause){
        super(message, cause);
    }
}
