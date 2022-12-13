package com.revature.aspects;

import com.revature.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<Object> handleNotLoggedInException(HttpServletRequest request, NotLoggedInException message) {
        String errorMessage = "Must be logged in to perform this action!";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<Object> handleInvalidLogin(HttpServletRequest request, NotLoggedInException message) {
        String errorMessage = "Invalid credentials!";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(DuplicateEmailFoundException.class)
    public ResponseEntity<Object> handleDuplicateEmailFoundException(HttpServletRequest request, DuplicateEmailFoundException message) {
        String errorMessage = "Email already taken!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(PasswordUnderAmountException.class)
    public ResponseEntity<Object> handlePasswordUnderAmount(HttpServletRequest request, DuplicateEmailFoundException message) {
        String errorMessage = "Password needs to be longer than 3 characters!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<Object> handleInvalidAccount(HttpServletRequest request, DuplicateEmailFoundException message) {
        String errorMessage = "Unable to transfer to this account!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(CheckRegisterFieldsException.class)
    public ResponseEntity<Object> handleRegisterFields(HttpServletRequest request, DuplicateEmailFoundException message) {
        String errorMessage = "Please fill in missing fields!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Object> handleInsufficientFundsException(HttpServletRequest request, InsufficientFundsException message) {
        String errorMessage = "Insufficient funds for this transaction!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Object> handleAuthorizationException(HttpServletRequest request, InsufficientFundsException message) {
        String errorMessage = "Invalid credentials to access this page!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(AppliedLoanException.class)
    public ResponseEntity<Object> handleAppliedLoanException(HttpServletRequest request, AppliedLoanException message){
        String errorMessage = "Please enter a valid input for all fields";
        return ResponseEntity.badRequest().body(errorMessage);
    }
    @ExceptionHandler(AppliedLoanPasswordException.class)
    public ResponseEntity<Object> handleAppliedLoanPasswordException(HttpServletRequest request, AppliedLoanPasswordException message){
        String errorMessage = "Passwords don't match! Enter your password into the 'password' field.";
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInputException(HttpServletRequest request, InvalidInputException message){
        String errorMessage = "Must input a valid number for transferring money.";
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(LoansNotFoundException.class)
    public ResponseEntity<Object> handleLoansNotFoundException(HttpServletRequest request, LoansNotFoundException message){
        String errorMessage = "No pending loans exist for this user.";
        return ResponseEntity.ok().body(errorMessage);
    }

    @ExceptionHandler(UserNotAllowedException.class)
    public ResponseEntity<Object> handleUserNotAllowedException(HttpServletRequest request, UserNotAllowedException message){
        String errorMessage = "Updating loan status is not permitted for this role.";
        return ResponseEntity.ok().body(errorMessage);
    }

    @ExceptionHandler(NoAlgException.class)
    public ResponseEntity<Object> handleNoAlgException(HttpServletRequest request, NoAlgException message) {
        String errorMessage = "No algorithm could be made.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<Object> handleInvalidAmountException(HttpServletRequest request, InvalidAmountException message) {
        String errorMessage = "Amount must be greater than zero.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Object> handleInvalidUserException(HttpServletRequest request, InvalidUserException message) {
        String errorMessage = "Cannot make payments from another user's accounts.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(ExceedsTotalLimitException.class)
    public ResponseEntity<Object> handleExceedsTotalLimitException(HttpServletRequest request, ExceedsTotalLimitException message) {
        String errorMessage = "Payment amount exceeds total limit.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
