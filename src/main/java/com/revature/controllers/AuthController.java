package com.revature.controllers;

import com.revature.annotations.Secured;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.UserDTO;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://good-bank-ui.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true", exposedHeaders = "Authorization")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest) {
        UserDTO userDetails = authService.loginCreds(loginRequest.getEmail(), loginRequest.getPassword());
        String token = tokenService.generateToken(userDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        return new ResponseEntity<>(userDetails, headers, HttpStatus.OK);
    }

    @PostMapping("/token-login")
    public ResponseEntity<UserDTO> tokenLogin(@RequestHeader("Authorization") String token) {
        UserDTO user = authService.tokenLogin(token);
        return ResponseEntity.ok(user);
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
    }
}
