package com.revature.exceptions;

public class ExceedsTotalLimitException extends RuntimeException{
    public ExceedsTotalLimitException() {}

    public ExceedsTotalLimitException(String message){
        super(message);
    }

    public ExceedsTotalLimitException(Throwable cause){
        super(cause);
    }

    public ExceedsTotalLimitException(String message, Throwable cause){
        super(message, cause);
    }
}
