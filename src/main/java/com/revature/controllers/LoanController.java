package com.revature.controllers;

import com.revature.dtos.LoanDTO;
import com.revature.dtos.LoanDetails;
import com.revature.models.Loan;
import com.revature.models.Status;
import com.revature.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://good-bank-ui.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true")
public class LoanController {

    @Autowired
    private LoanService ls;
    @PostMapping("/{id}")
    public ResponseEntity<Loan> appliedLoan(@PathVariable("id") int userId, @RequestBody LoanDTO loan){
        Loan newLoan = ls.createLoan(loan, userId);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Loan>> getUserLoans(@PathVariable("id") int userId){
        List<Loan> loans = ls.getUserLoans(userId);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @GetMapping("/pending-loans")
    public ResponseEntity<List<Loan>> getPendingLoans(@RequestHeader("Current-User") String userType){
        List<Loan> loans = ls.getPendingLoans(userType);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @PutMapping("/pending-loans/{id}")
    public ResponseEntity<Loan> updateStatus(@RequestHeader("Current-User") String userType, @PathVariable("id") int loanID, @RequestBody Loan loan){
        Loan updateLoan = ls.updateLoanStatus(userType, loanID, loan);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

}
