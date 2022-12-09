package com.revature.dtos;

import com.revature.models.Loan;
import com.revature.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanDetails that = (LoanDetails) o;
        return loanID == that.loanID && userId == that.userId && Double.compare(that.initialAmount, initialAmount) == 0 && Double.compare(that.balance, balance) == 0 && Objects.equals(reason, that.reason) && Objects.equals(creationDate, that.creationDate) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanID, userId, reason, initialAmount, balance, creationDate, status);
    }

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
