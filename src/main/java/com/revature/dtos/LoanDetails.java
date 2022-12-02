package com.revature.dtos;

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
    private String reason;
    private double initialAmount;
    private double balance;
    private int creationDate;
    private String status;
}
