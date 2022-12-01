package com.revature.dtos;

import com.revature.models.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String state;
    private String city;
    private int zip;
}
