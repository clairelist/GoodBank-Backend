package com.revature.controllers;

import com.revature.dtos.ResetRequest;
import com.revature.dtos.UpdateRequest;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:3000", "http://good-bank-ui.s3-website-us-west-2.amazonaws.com"}, allowedHeaders = "*", allowCredentials = "true")
public class UserController {
    private final UserService us;

    public UserController(UserService us) {
        this.us = us;
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<User> resetPass(@RequestBody ResetRequest update) {
        // User res; USED FOR TESTING ONLY!
        ResponseEntity<User> response = null;
        try {
            if (us.updatePassword(update) == null) {
                response = ResponseEntity.badRequest().build();
            } else {
                response = ResponseEntity.ok().build();
            }

        } catch (Exception e) {
            response = ResponseEntity.badRequest().build();
        }
        return response;

    }


    @PutMapping("/profile")
    public ResponseEntity<User> update(@RequestBody UpdateRequest updateRequest) {
        if (us.updateProfile(updateRequest) == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.accepted().build();
        }
    }
}
