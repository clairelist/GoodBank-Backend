package com.revature.services;

import com.revature.dtos.LoanDTO;
import com.revature.models.Loan;
import com.revature.models.User;
import com.revature.repositories.LoanRepository;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class LoanService {

    private final UserRepository ur;

    private final UserService us;

    private final LoanRepository lr;

    @Autowired
    public LoanService(UserRepository ur, UserService us, LoanRepository lr) {
        this.ur = ur;
        this.us = us;
        this.lr = lr;
    }

    public Loan createLoan(LoanDTO appliedLoan, int userId) {
        Loan newLoan = new Loan();
        User user = ur.getById(userId);
        newLoan.setInitialAmount(appliedLoan.getInitialAmount());
        newLoan.setReason(appliedLoan.getReason());
        newLoan.setUser(appliedLoan.getUserId());
        newLoan.setCreationDate(Date.from(Instant.now()));
        newLoan.setBalance(appliedLoan.getInitialAmount());
        newLoan.setUser(user);
//        newLoan.setStatus(PENDING);
        lr.save(newLoan);

        // TODO make sure to create corresponding transaction on account?

        return newLoan;

    }

    public List<Loan> getUserLoans(int userId) {
        User user = us.findById(userId);

        List<Loan> loans = lr.findByUser(user);
        return loans;
    }
}
