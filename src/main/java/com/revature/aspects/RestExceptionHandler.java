package com.revature.aspects;

import com.revature.exceptions.DuplicateEmailFoundException;
import com.revature.exceptions.InsufficientFundsException;
import com.revature.exceptions.NotLoggedInException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<Object> handleNotLoggedInException(HttpServletRequest request, NotLoggedInException notLoggedInException) {

        String errorMessage = "Must be logged in to perform this action";

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(DuplicateEmailFoundException.class)
    public ResponseEntity<Object> handleDuplicateEmailFoundException(HttpServletRequest request, DuplicateEmailFoundException DuplicateEmailFoundException) {

        String errorMessage = "Email already taken";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Object> handleInsufficientFundsException(HttpServletRequest request, InsufficientFundsException message) {

        String errorMessage = "Insufficient funds for this transaction";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
