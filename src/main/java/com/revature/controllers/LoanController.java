package com.revature.controllers;

import com.revature.annotations.Secured;
import com.revature.dtos.LoanDTO;
import com.revature.dtos.LoanDetails;
import com.revature.models.Loan;
import com.revature.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://good-bank-ui.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true", exposedHeaders = "Authorization")
public class LoanController {

    @Autowired
    private LoanService ls;

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PostMapping("/{id}")
    public ResponseEntity<LoanDetails> appliedLoan(@PathVariable("id") int userId, @RequestBody LoanDTO loan){
        LoanDetails newLoan = ls.createLoan(loan, userId);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @GetMapping("/{id}")
    public ResponseEntity<List<Loan>> getUserLoans(@PathVariable("id") int userId){
        List<Loan> loans = ls.getUserLoans(userId);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @GetMapping("/pending-loans")
    public ResponseEntity<List<LoanDetails>> getPendingLoans(@RequestHeader("Authorization") String token){
        List<LoanDetails> loans = ls.getPendingLoans(token);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PutMapping("/pending-loans")
    public ResponseEntity<LoanDetails> updateStatus(@RequestHeader("Authorization") String token, @RequestBody LoanDetails loan){
        LoanDetails updateLoan = ls.updateLoanStatus(token, loan);
        return new ResponseEntity<>(updateLoan, HttpStatus.OK);
    }

}
