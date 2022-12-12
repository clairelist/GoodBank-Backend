package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
//comment
@Entity
@Table(name = "creditcards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private User user; //emulates a customerId

    private Long cardNumber;
    private Integer ccv;
    private Date expirationDate;

    private double totalLimit;
    private double availableBalance;

    @Enumerated(EnumType.STRING)
    private Status status;

}
