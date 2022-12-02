package com.revature.dtos;

import com.revature.models.Loan;
import com.revature.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetails {
    private int loanID;
    private int userId;
    private String reason;
    private double initialAmount;
    private double balance;
    private Date creationDate;
    private String status;

    public LoanDetails(Loan newLoan) {
        this.loanID = newLoan.getId();
        this.userId = newLoan.getUser().getId();
        this.reason = newLoan.getReason();
        this.initialAmount = newLoan.getInitialAmount();
        this.balance = newLoan.getBalance();
        this.creationDate = newLoan.getCreationDate();
        this.status = String.valueOf(newLoan.getStatus());
    }
}
