package com.revature.controllers;

import com.revature.annotations.Secured;
import com.revature.dtos.ResetRequest;
import com.revature.dtos.UpdateRequest;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.InvalidUserException;
import com.revature.models.User;
import com.revature.services.MailService;
import com.revature.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://good-bank-ui.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true", exposedHeaders = "Authorization")
public class UserController {
    private final UserService us;

    public UserController(UserService us) {
        this.us = us;
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<User> resetPass(@RequestBody ResetRequest update) {
        //I NEED TO REFACTOR RESETREQUEST DTO
        //TODO: I do not work this way anymore! I need to recieve the user_id, and the new password.
        //this user id is a hashed value from client application (client app gets from url param; comes from link sent to user)
        //decode the user id
        //check that that user exists
        //update password.

        User user = us.updatePassword(update);
        try {
            if (user == null) {
                return ResponseEntity.badRequest().build();
            } else {
                return ResponseEntity.ok(user);
            }
        } catch (Exception e) {
            throw new InvalidUserException();
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> getSecurityQuestion(@RequestBody ResetRequest user_email){

        Optional<User> found = us.findByEmail(user_email.getEmail());
        String response = null;
        ResponseEntity<String> entity = null;

        if (found.isPresent()){
            response = found.get().getSecurityQuestion();
            entity = ResponseEntity.ok(response);
        } else {
            entity = ResponseEntity.badRequest().build();
        }
        return entity;
}

    @PostMapping("/request-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestParam CharSequence id, @RequestBody String email){ //reset-request?id=xyz
        //I call mail service, send email.
        //Optional<User> found = us.findByEmail(user_email.getEmail());
        String response = "You sucessfully submitted a request to reset your password. \n If an account exists for the email address you submitted, you will recieve an email with a link to reset your password shortly.";
        ResponseEntity<String> entity = null;

        MailService mailer = new MailService();

        //then, check that the user exists
        //THEN, send email.
        mailer.sendMail(email, id);

        entity = ResponseEntity.ok(response);
        return entity;
    }


    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PatchMapping("/profile")
    public ResponseEntity<UserDTO> update(@RequestBody UpdateRequest updateRequest) {
        UserDTO updatedProfile = us.updateProfile(updateRequest);

        if (updatedProfile == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(updatedProfile);
        }
    }
}
