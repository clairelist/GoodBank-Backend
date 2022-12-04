package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardTransactionDTO {

    private Integer id;
    private double amount;
    private Date creationDate;
    private Integer creditCardId;
    private Integer accountId;



}

