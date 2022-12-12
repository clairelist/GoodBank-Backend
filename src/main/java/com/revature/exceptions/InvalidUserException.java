package com.revature.exceptions;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException() {}

    public InvalidUserException(String message){
        super(message);
    }

    public InvalidUserException(Throwable cause){
        super(cause);
    }

    public InvalidUserException(String message, Throwable cause){
        super(message, cause);
    }
}