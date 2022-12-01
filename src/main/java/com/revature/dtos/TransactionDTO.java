package com.revature.dtos;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Integer id;
    private double amount;
    private String description;
    private TransactionType type;
    private Date creationDate;
    private Account account;
    private Integer toAccountId;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.type = transaction.getType();
        this.creationDate = transaction.getCreationDate();
        this.account = transaction.getAccount();
        this.toAccountId = transaction.getToAccountId();
    }
}
