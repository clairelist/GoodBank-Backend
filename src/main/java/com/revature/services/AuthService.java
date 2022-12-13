package com.revature.services;

import com.revature.dtos.NotificationCreationRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.UserDTO;
import com.revature.dtos.UpdateRequest;
import com.revature.exceptions.CheckRegisterFieldsException;
import com.revature.exceptions.DuplicateEmailFoundException;
import com.revature.exceptions.InvalidLoginException;
import com.revature.exceptions.PasswordUnderAmountException;
import com.revature.models.NotificationType;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final NotificationService notificationService;

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserService userService, UserRepository userRepository, NotificationService notificationService, TokenService tokenService) {

        this.userService = userService;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.tokenService = tokenService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserDTO loginCreds(String email, String password) {
        if (email.trim().equals("") || password.trim().equals("")) {
            throw new InvalidLoginException();
        }
        return userService.loginCreds(email, password);
    }

    public User register(RegisterRequest register) {
        if(userService.findByEmail(register.getEmail().toLowerCase()).isPresent()){
            throw new DuplicateEmailFoundException();
        } else if (register.getPassword().length() <= 3) {
            throw new PasswordUnderAmountException();
        } else if ((register.getEmail().trim().equals("") || register.getPassword().trim().equals("") ||
                register.getFirstName().trim().equals("") || register.getLastName().trim().equals(""))) {
            throw new CheckRegisterFieldsException();
        } else {
            User user = new User(register);
            user.setPassword(this.passwordEncoder.encode(register.getPassword().trim()));
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

    public UserDTO tokenLogin(String token) {
        UserDTO currentUser = tokenService.extractTokenDetails(token);
        User user = userRepository.getById(currentUser.getId());
        return new UserDTO(user);
    }
}
