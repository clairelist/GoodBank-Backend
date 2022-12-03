package com.revature.controllers;

import com.revature.dtos.ResetRequest;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
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


}