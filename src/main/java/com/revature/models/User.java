package com.revature.models;

import com.revature.dtos.RegisterRequest;
import com.revature.dtos.UpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String state;
    private String city;
    private int zip;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private Date creationDate;

    private String securityQuestion; //used for reset password flow, should default to ''. For the record I think camel casing our entitites is a dumb idea, because no database in the world uses that. Everyone knows to use an underscore.
    private String securityAnswer;

    public User(RegisterRequest register) {
        this.email = register.getEmail().toLowerCase();
        this.password = register.getPassword();
        this.firstName = register.getFirstName();
        this.lastName = register.getLastName();
        this.address = register.getAddress();
        this.state = register.getState();
        this.city = register.getCity();
        this.zip = register.getZip();
        this.securityQuestion = register.getSecurityQuestion();
        this.securityAnswer = register.getSecurityAnswer();
    }

    public User(UpdateRequest update) {
        this.email = update.getEmail();
        this.firstName = update.getFirstName();
        this.lastName = update.getLastName();
        this.address = update.getAddress();
        this.state = update.getState();
        this.city = update.getCity();
        this.zip = update.getZip();
    }
}
