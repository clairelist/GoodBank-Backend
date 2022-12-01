package com.revature.services;

import com.revature.BankingApplication;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = BankingApplication.class)
public class UserServiceTest {

    @MockBean
    private UserRepository ur;

    @Autowired
    private UserService us;

    @Test
    public void updatePasswordPerformsUpdate(){
        //TODO: I SHOULD FOLLOW THE SAME LOGIC AS UPDATING A RECIPE FROM BEFORE.
        User beforeUser = new User();
        beforeUser.setPassword("old password");

        String newPassword = "NEW password!";

        //MOCK the call to the respotiyurieor.
        //expect(beforeUser.password).toEqual(us.update(beforeUser.id, newPassword))
    }
}
