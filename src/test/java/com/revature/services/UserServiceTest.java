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
        beforeUser.setId(1);
        beforeUser.setPassword("old password");


        String newPassword = "NEW password!";
        User updatedUser = new User();
        updatedUser.setPassword(newPassword);



        //MOCK the call to the respotiyurieor.
        //expect(beforeUser.password).toEqual(us.update(1, newPassword))
        Mockito.when(us.updatePassword(1, newPassword)).thenReturn(updatedUser);

        assertEquals(updatedUser.getPassword(), newPassword);
    }
}
