package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Account receiverAccount;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private User senderUser;

    private double amount;

    @Enumerated(EnumType.STRING)
    private Status status;

}
