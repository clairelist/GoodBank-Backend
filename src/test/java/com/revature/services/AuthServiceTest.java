package com.revature.services;

import com.revature.BankingApplication;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.NotificationCreationRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.DuplicateEmailFoundException;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes= BankingApplication.class)
  class AuthServiceTest {
    @MockBean
    private UserRepository mockRepository;

    @MockBean
    private NotificationService monkNs;

    @MockBean
    private UserService us;

    @Autowired
    private AuthService sut;

    @Test
     void findByCredsCorrect(){
        //Arrange
        LoginRequest creds = new LoginRequest();
        creds.setEmail("test@gmail.com");
        creds.setPassword("pass");
        UserDTO expected = us.loginCreds(creds.getEmail(), creds.getPassword());

        Mockito.when(mockRepository.findByEmailAndPassword(creds.getEmail(), creds.getPassword())).thenReturn(expected);
        Mockito.when(us.loginCreds(creds.getEmail(), creds.getPassword())).thenReturn(expected);
        //Act
        UserDTO actual = sut.loginCreds(creds.getEmail(), creds.getPassword());
        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void registerUserThrowsError(){
        RegisterRequest register = new RegisterRequest();
        register.setEmail("test@gmail.com");
        register.setPassword("tests");
        register.setFirstName("reg");
        register.setLastName("reg");
        register.setAddress("123 A St");
        register.setState("NC");
        register.setCity("WS");
        register.setZip(30547);
        register.setSecurityQuestion("What flavor of GUNDAM do you fly?");
        register.setSecurityAnswer("Akai the colour of the sky");
        User registered = new User(register);
        mockRepository.save(registered);
        User newUser = new User(register);

        Mockito.when(us.findByEmail(newUser.getEmail())).thenReturn(Optional.of(newUser));

        assertThrows(DuplicateEmailFoundException.class, () -> sut.register(register));
    }

    @Test
     void registerUserCreates(){
        RegisterRequest registerreq = new RegisterRequest();
        registerreq.setEmail("test@gmail.com");
        registerreq.setPassword("test");
        registerreq.setFirstName("reg");
        registerreq.setLastName("reg");
        registerreq.setAddress("123 A St");
        registerreq.setState("NC");
        registerreq.setCity("WS");
        registerreq.setZip(30547);
        registerreq.setSecurityQuestion("What flavor of GUNDAM do you fly?");
        registerreq.setSecurityAnswer("Akai the colour of the sky");


        Mockito.when(us.findByEmail(registerreq.getEmail())).thenReturn(Optional.empty());
        Mockito.when(monkNs.create(Mockito.any(NotificationCreationRequest.class))).thenReturn(null);

        User actual = sut.register(registerreq);
        actual.setCreationDate(Date.from(Instant.now()));
        User expected = new User(registerreq);
        expected.setUserType(UserType.CLIENT);
        expected.setCreationDate(Date.from(Instant.now()));

        assertEquals(expected, actual);
    }
}

