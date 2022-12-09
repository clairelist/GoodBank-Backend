package com.revature.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.models.Account;
import com.revature.models.AccountType;
import com.revature.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Integer id;
    private String name;
    private Double balance;

    private AccountType accountType;
    private Date creationDate;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private User user;

    public AccountDTO(Account stubAccount) {
    }
}
