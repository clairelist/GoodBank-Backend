package com.revature.controllers;

import com.revature.dtos.LoanDTO;
import com.revature.models.Loan;
import com.revature.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
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


}
