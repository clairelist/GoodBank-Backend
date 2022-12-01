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

@Service
public class LoanService {
    @Autowired
    private UserRepository ur;

    @Autowired
    private LoanRepository lr;
    public Loan createLoan(LoanDTO appliedLoan, int userId) {
        Loan newLoan = new Loan();
//        Account account = accountRepository.findById(appliedLoan.getUserId().getId()).orElse(null);
        User user = ur.getById(userId);
        newLoan.setInitialAmount(appliedLoan.getInitialAmount());
        newLoan.setReason(appliedLoan.getReason());
        newLoan.setUser(appliedLoan.getUserId());
        newLoan.setCreationDate(Date.from(Instant.now()));
        newLoan.setBalance(appliedLoan.getInitialAmount());
//        newLoan.setStatus(PENDING);
        lr.save(newLoan);

        return newLoan;

    }
}
