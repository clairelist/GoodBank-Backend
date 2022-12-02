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

        User beforeUser = new User();
        beforeUser.setId(1);
        beforeUser.setPassword("old password");

        String newPassword = "NEW password!";
        User updatedUser = new User();
        updatedUser.setPassword(newPassword);

        Mockito.when(us.updatePassword(1, newPassword)).thenReturn(updatedUser);
        assertEquals(updatedUser.getPassword(), newPassword);
    }

    @Test
    public void getUserByIdExists(){
        User user = new User();
        user.setId(1);

        Mockito.when(us.findById(1)).thenReturn(user);
        assertEquals(user, us.findById(1));
    }
}
