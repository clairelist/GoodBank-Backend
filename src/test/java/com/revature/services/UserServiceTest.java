package com.revature.services;

import com.revature.BankingApplication;
import com.revature.dtos.ResetRequest;
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
public class UserServiceTest {
    @MockBean
    private UserRepository mockRepository;

    @Autowired
    private UserService sut;

    @Test
    public void updatePassword() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@test.org");
        user.setPassword("originalPass");

        ResetRequest reset = new ResetRequest();
        reset.setEmail("test@test.org");
        reset.setPassword("newpass");

        User newPass = new User();
        user.setId(1);
        newPass.setEmail("test@test.org");
        newPass.setPassword("newpass");

        Mockito.when(mockRepository.findByEmail("test@test.org")).thenReturn(Optional.of(user));
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(mockRepository.save(newPass)).thenReturn(newPass);
        User actual = sut.updatePassword(reset);
        assertEquals(newPass, actual);
        System.out.println("Expected: " + newPass.getPassword());
        System.out.println("Actual: " + actual.getPassword());

    }
}

