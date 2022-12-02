package com.revature.dtos;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
    private double amount;
    private Account account;
    private TransactionType type;
    private Integer toAccountId;

    public TransferDTO(Transaction transfer) {
        this.amount = transfer.getAmount();
        this.account = transfer.getAccount();
        this.type = transfer.getType();
        this.toAccountId = transfer.getToAccountId();
    }
}
