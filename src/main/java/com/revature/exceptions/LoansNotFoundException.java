package com.revature.exceptions;

public class LoansNotFoundException extends RuntimeException{
    public LoansNotFoundException() {}

    public LoansNotFoundException(String message){
        super(message);
    }

    public LoansNotFoundException(Throwable cause){
        super(cause);
    }

    public LoansNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
