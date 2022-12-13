package com.revature.services;

import com.revature.BankingApplication;
import com.revature.dtos.UserDTO;
import com.revature.models.User;
import com.revature.models.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes= BankingApplication.class)
@AutoConfigureMockMvc
class TokenServiceTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private TokenService ts;

    @Test
    void shouldGenerateAuthToken() {
        User stubUser = new User(
                1,
                "lilmissgogetta@revature.com",
                "pass",
                "Lesly",
                "Gonzalez",
                "1234 Revature Lane",
                "Cleveland",
                "Ohio",
                44102,
                UserType.CLIENT,
                new Date(System.currentTimeMillis()),
                "What is your favorite ANIME?",
                "Something cool"
        );
        UserDTO newUser = new UserDTO(stubUser);
        String token = ts.generateToken(newUser);
//        System.out.println("TOKEN: " + token);
        assertNotNull(token);
    }

    @Test
    void shouldExtractAuthToken() {
        User stubUser = new User(
                1,
                "lilmissgogetta@revature.com",
                "pass",
                "Lesly",
                "Gonzalez",
                "1234 Revature Lane",
                "Cleveland",
                "Ohio",
                44102,
                UserType.CLIENT,
                new Date(System.currentTimeMillis()),
                "What is your favorite ANIME?",
                "Something cool"
        );
        UserDTO newUser = new UserDTO();
        newUser.setId(stubUser.getId());
        newUser.setEmail(stubUser.getEmail());
        newUser.setType(stubUser.getUserType());
        String token = ts.generateToken(newUser);

        UserDTO actualDTO = ts.extractTokenDetails(token);
//        System.out.println("DTO USER: " + actualDTO);
        assertEquals(newUser, actualDTO);
    }
}
