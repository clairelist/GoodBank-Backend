package com.revature.controllers;

import com.revature.dtos.ResetRequest;
import com.revature.models.User;
import com.revature.services.UserService;
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
    public ResponseEntity<User> resetPass(@RequestBody ResetRequest update){
        ResponseEntity<User> response = null;
        try {
            if(us.updatePassword(update) == null){
                response = ResponseEntity.badRequest().build();
            } else {
                response = ResponseEntity.ok().build();
            }
        } catch(Exception e) {
            response = ResponseEntity.badRequest().build();
        }
    return response;

    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> getSecurityQuestion(@RequestBody ResetRequest user_email){

        Optional<User> found = us.findByEmail(user_email.getEmail());
        String response = null;
        ResponseEntity<String> entity = null; //gee I sure am glad to use a strongly typed language! =_=

        if (found.isPresent()){
            response = found.get().getSecurityQuestion();
            entity = ResponseEntity.ok(response); //test if I actually return the security question string!
        } else {
            entity = ResponseEntity.badRequest().build();
        }
        //TODO: THE CLIENT SENDS THE ANSWER TO THE ABOVE ^^^PATCH REQUEST^^^
        return entity;
    }
}
