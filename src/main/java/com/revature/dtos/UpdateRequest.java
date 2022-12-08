package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequest {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String state;
    private String city;
    private int zip;
}


