package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.dtos.TransactionDTO;
import com.revature.dtos.TransferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double amount;
    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private Date creationDate;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private Account account;

    @Column(nullable = true)
    private Integer toAccountId; // utilized when doing transfers, not showing in the other button field for general transaction

    public Transaction(TransactionDTO transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.type = transaction.getType();
        this.creationDate = transaction.getCreationDate();
        this.account = transaction.getAccount();
        this.toAccountId = transaction.getToAccountId();
    }

    public Transaction(TransferDTO transfer) {
        this.amount = transfer.getAmount();
        this.account = transfer.getAccount();
        this.type = transfer.getType();
        this.toAccountId = transfer.getToAccountId();
    }
}
