package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://good-bank-ui.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true")
public class UserController {
    private final UserService us;

    public UserController(UserService us) {
        this.us = us;
    }

    @PatchMapping("/{id}/reset-password")
    public ResponseEntity<User> resetPass(@PathVariable("id") int id, @RequestBody String update){
        //TODO: MAY HAVE TO CHANGE WHAT WE SEND THRU
        User res = null;
        ResponseEntity response = null;
        try {
            if(us.updatePassword(id, update) == null){
                response = ResponseEntity.badRequest().build();
            } else {
                res = us.updatePassword(id, update);
                response = ResponseEntity.ok(res);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    return response;

    }
}
