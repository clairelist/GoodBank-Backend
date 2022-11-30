package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private CreditCard creditCard;

}
