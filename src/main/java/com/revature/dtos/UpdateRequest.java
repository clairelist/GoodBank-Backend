package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UpdateRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String state;
    private String city;
    private int zip;

};


