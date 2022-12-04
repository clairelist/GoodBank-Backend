package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.dtos.CreditCardTransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cctransactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double amount;
    private String description;
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    private CreditCardTransactionType type;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private CreditCard creditCard;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private Account account;


    public CreditCardTransaction(CreditCardTransactionDTO creditCardTransactionDTO) {
        this.amount = creditCardTransactionDTO.getAmount();
    }
}
