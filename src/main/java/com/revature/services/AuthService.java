package com.revature.services;

import com.revature.dtos.RegisterRequest;
import com.revature.dtos.UserDTO;
import com.revature.dtos.UpdateRequest;
import com.revature.exceptions.DuplicateEmailFoundException;
import com.revature.models.User;
import com.revature.models.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;

    @Autowired
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public UserDTO loginCreds(String email, String password) {
        return userService.loginCreds(email, password);
    }

    public User register(RegisterRequest register) {

        if(userService.findByEmail(register.getEmail()).isPresent()){
            throw new DuplicateEmailFoundException("Email already taken");
        } else {
            User user = new User(register);
            user.setUserType(UserType.CLIENT);
            user.setCreationDate(Date.from(Instant.now()));
            userService.save(user);
            return user;
        }
    }

    public User update(UpdateRequest update) {
        User user = new User(update);
        userService.save(user);
        return user;
    }
}
