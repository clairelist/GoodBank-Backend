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
    public ResponseEntity<LoanDetails> appliedLoan(@PathVariable("id") int userId, @RequestBody LoanDTO loan){
        LoanDetails newLoan = ls.createLoan(loan, userId);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Loan>> getUserLoans(@PathVariable("id") int userId){
        List<Loan> loans = ls.getUserLoans(userId);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @GetMapping("/pending-loans")
    public ResponseEntity<List<LoanDetails>> getPendingLoans(@RequestHeader("Current-User") String userType){
        List<LoanDetails> loans = ls.getPendingLoans(userType);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @PutMapping("/pending-loans")
    public ResponseEntity<Loan> updateStatus(@RequestHeader("Current-User") String userType, @RequestBody LoanDetails loan){
        Loan updateLoan = ls.updateLoanStatus(userType, loan);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

}
