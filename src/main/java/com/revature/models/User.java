package com.revature.models;

import com.revature.dtos.RegisterRequest;
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

    public User(RegisterRequest register) {
        this.email = register.getEmail();
        this.password = register.getPassword();
        this.firstName = register.getFirstName();
        this.lastName = register.getLastName();
        this.address = register.getAddress();
        this.state = register.getState();
        this.city = register.getCity();
        this.zip = register.getZip();
    }
}
