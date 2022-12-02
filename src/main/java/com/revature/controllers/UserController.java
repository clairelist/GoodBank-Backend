package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
        //User res = null;
        ResponseEntity response = null;
        try {
            if(us.updatePassword(id, update) == null){
                response = ResponseEntity.badRequest().build();
            } else {
              //  res = us.updatePassword(id, update);
                response = ResponseEntity.ok().build();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    return response;

    }

//    @PostMapping("/forgot-password")
//    public void forgotPass(@RequestBody String email){
//        //we recieve the email of the user,
//        //check if that email is valid ie belongs to an account
//        //if yes, send password reset email using SpringMailService
//        //if no, do nothing basically
//        Optional<User> found = us.findByEmail(email);
//        if(found.isPresent()){
//            us.sendEmail(email);
//        }
//    }
}
