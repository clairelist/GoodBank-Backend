package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.dtos.AccountDTO;
import com.revature.dtos.TransferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double balance;

    private Date creationDate;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private User user; //emulates a customerId

    public Account(String name, Date creationDate, AccountType accountType, User user) {
        this.name = name;
        this.creationDate = creationDate;
        this.accountType = accountType;
        this.user = user;
    }

    public Account(AccountDTO newaccount) {
        this.id = newaccount.getId();
        this.name = newaccount.getName();
        this.balance = newaccount.getBalance();
        this.creationDate = newaccount.getCreationDate();
        this.accountType = newaccount.getAccountType();
        this.user = newaccount.getUser();
    }

}
