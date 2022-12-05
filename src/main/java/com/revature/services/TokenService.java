package com.revature.services;

import com.revature.dtos.UserDTO;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.repositories.UserRepository;
import com.revature.utils.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    private UserRepository ur;
    private JwtConfig jc;

    @Autowired
    public TokenService(UserRepository ur, JwtConfig jc) {
        this.ur = ur;
        this.jc = jc;
    }

    public String generateToken(UserDTO userDetails) {
        long current = System.currentTimeMillis();

        return Jwts.builder()
                .setId(userDetails.getId().toString())
                .claim("email", userDetails.getEmail())
                .claim("type", userDetails.getType())
                .setIssuedAt(new Date(current))
                .setExpiration(new Date(current + jc.getExpiration()))
                .signWith(jc.getSigningKey(), jc.getSigAlg())
                .compact();
    }
    public UserDTO extractTokenDetails(String token) {
        if (token == null || token.isEmpty()) {
            throw new NotLoggedInException();
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jc.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        User currentUser = ur.findById(Integer.valueOf(claims.getId())).orElseThrow(() -> new NotLoggedInException());

        UserDTO returnUser = new UserDTO();
        returnUser.setId(Integer.valueOf(claims.getId()));
        returnUser.setEmail(claims.get("email", String.class));
        returnUser.setType(UserType.valueOf(claims.get("type", String.class)));

        return returnUser;
    }
}
