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
    private UserType type;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.type = user.getUserType();
    }
}
