package com.revature.services;

import com.revature.dtos.LoanDTO;
import com.revature.dtos.LoanDetails;
import com.revature.models.Loan;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.repositories.LoanRepository;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class LoanService {
    @Autowired
    private UserRepository ur;
    @Autowired
    private UserService us;
    @Autowired
    private LoanRepository lr;
    public Loan createLoan(LoanDTO appliedLoan, int userId) {
        Loan newLoan = new Loan();
        User user = ur.getById(userId);
        newLoan.setInitialAmount(appliedLoan.getInitialAmount());
        newLoan.setReason(appliedLoan.getReason());
        newLoan.setUser(appliedLoan.getUserId());
        newLoan.setCreationDate(Date.from(Instant.now()));
        newLoan.setBalance(appliedLoan.getInitialAmount());
        newLoan.setUser(user);
        newLoan.setStatus(Status.PENDING);
        lr.save(newLoan);

        // TODO make sure to create corresponding transaction on account?

        return newLoan;

    }

    public List<Loan> getUserLoans(int userId) {
        User user = us.findById(userId);

        List<Loan> loans = lr.findByUser(user);
        return loans;
    }

    public List<Loan> getPendingLoans(String userType){
        if(Objects.equals(userType, UserType.ADMIN.toString())){
            List<Loan> loans = lr.findByStatus(Status.PENDING);
            return loans;
        }
        return null;
    }


//    public Loan updateLoanStatus(String userType, int loanID, LoanDetails loanDetails) {
//        Loan loan = lr.getById(loanID);
//        if(Objects.equals(loanDetails.getStatus(), "APPROVED")){
//            loan.setStatus(Status.APPROVED);
//        } else if(Objects.equals(loanDetails.getStatus(), "DENIED")){
//            loan.setStatus(Status.DENIED);
//        }
//        lr.save(loan);
//        return loan;
//    }

    public Loan updateLoanStatus(String userType, int loanID, Loan updateLoan) {
        Loan loan = lr.getById(loanID);
        if(Objects.equals(updateLoan.getStatus(), "APPROVED")){
            loan.setStatus(Status.APPROVED);
        } else if(Objects.equals(updateLoan.getStatus(), "DENIED")){
            loan.setStatus(Status.DENIED);
        }
        loan.setReason(loan.getReason());
        loan.setBalance(loan.getBalance());
        loan.setCreationDate(loan.getCreationDate());
        loan.setInitialAmount(loan.getInitialAmount());
        lr.save(loan);
        return loan;
    }
}
