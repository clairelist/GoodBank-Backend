
package com.revature.services;

import com.revature.BankingApplication;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.UserDTO;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes= BankingApplication.class)
class UserServiceTest {
    @MockBean
    private UserRepository mockRepository;

    @Autowired
    private UserService sut;


    @Test
    void successLogin(){
        LoginRequest creds = new LoginRequest();

        creds.setEmail("test@email.com");
        creds.setPassword("pass");

        User user = new User();

        user.setEmail(creds.getEmail());
        user.setPassword(creds.getPassword());
        UserDTO actual = new UserDTO(user);

        Mockito.when(mockRepository.findByEmailAndPassword(creds.getEmail(), creds.getPassword())).thenReturn(actual);

        UserDTO expected = sut.loginCreds(creds.getEmail(), creds.getPassword());

        assertEquals(expected, actual);

        System.out.println(expected);
        System.out.println(actual);
    }

}

