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
       // User res; USED FOR TESTING ONLY!
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
    public String getSecurityQuestion(@RequestBody ResetRequest user_email){ //TODO:: I NEED TO CHANGE THIS
        //I FETCH THE SECURITY QUESTION FROM THE USER'S PASSED IN EMAIL,
        //IF I RETURN A 200, SEND BACK QUESTION

        //response = ResponseEntity.ok().build();
        Optional<User> found = us.findByEmail(user_email.getEmail());
        String response = null;

        if (found.isPresent()){
            response = found.get().getSecurityQuestion();
        }

        //THEN THE CLIENT SENDS THE ANSWER TO THE ABOVE ^^^PATCH REQUEST^^^
        //TODO: IN USER SERVICE, BE SURE TO SET PASSWORD IN THE RESETREQUEST OBJECT TO NULL
        //BUT, BE SURE THE RESPONSE ENTITY HAS THE SECURITY QUESTION


        //blah blah, try/catch?? why dont you TRY to CATCH some b******?
        return response;
    }

}
