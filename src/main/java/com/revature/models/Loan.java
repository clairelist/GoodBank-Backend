package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double initialAmount;
    private double balance;
    private String reason;
    private Date creationDate;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private User user;
    @Enumerated(EnumType.STRING)
    private Status status;


}
