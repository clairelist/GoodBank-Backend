package com.revature.exceptions;

public class AppliedLoanPasswordException extends RuntimeException{
    public AppliedLoanPasswordException(){}

    public AppliedLoanPasswordException(String message){
        super(message);
    }

    public AppliedLoanPasswordException(Throwable cause){
        super(cause);
    }

    public AppliedLoanPasswordException(String message, Throwable cause){
        super(message, cause);
    }
}
