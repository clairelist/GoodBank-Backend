package com.revature.dtos;

import com.revature.models.User;
import com.revature.models.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String state;
    private String city;
    private int zip;
    private UserType type;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.type = user.getUserType();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.address = user.getAddress();
        this.state = user.getState();
        this.city = user.getCity();
        this.zip = user.getZip();
    }
}
