package com.revature.services;

import com.revature.dtos.NotificationCreationRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.UserDTO;
import com.revature.dtos.UpdateRequest;
import com.revature.exceptions.DuplicateEmailFoundException;
import com.revature.models.NotificationType;
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
    private final NotificationService notificationService;

    @Autowired
    public AuthService(UserService userService, NotificationService notificationService) {

        this.userService = userService;
        this.notificationService = notificationService;
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

            // demonstrating notification creation
            // step 1, create a NotificationCreationRequest
            NotificationCreationRequest notif = new NotificationCreationRequest();
            // step 2, pass it the target user, the notification type, and message
            notif.setUser(user);
            notif.setType(NotificationType.INFORMATION);
            notif.setBody("Welcome " + user.getFirstName() + "! You account has been successfully created. We hope you enjoy banking with us!");
            // note: you can also use setReferencesId() to set a reference id to an
            // account or transfer or loan ect. as applicable, but it is not required
            // in this example

            //step 3, pass the request into notificationService.create()
            notificationService.create(notif);

            return user;
        }
    }

    public User update(UpdateRequest update) {
        User user = new User(update);
        userService.save(user);
        return user;
    }
}
