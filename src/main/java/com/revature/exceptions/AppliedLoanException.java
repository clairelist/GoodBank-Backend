package com.revature.exceptions;

public class AppliedLoanException extends RuntimeException{
    public AppliedLoanException(){}

    public AppliedLoanException(String message){
        super(message);
    }

    public AppliedLoanException(Throwable cause){
        super(cause);
    }

    public AppliedLoanException(String message, Throwable cause){
        super(message, cause);
    }
}
