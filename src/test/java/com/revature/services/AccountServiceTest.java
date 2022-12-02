package com.revature.services;

import com.revature.BankingApplication;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes= BankingApplication.class)
public class AccountServiceTest {
    @MockBean
    private AccountRepository mockRepository;

    @Autowired
    private AccountService sut;
    private UserService us;

//    @Test
//    public void findByIdSendsBackUser(){
//        //Arrange
//        User newUser = new User();
//        newUser.setId(1);
//        newUser.setEmail("test@gmail.com");
//        newUser.setPassword("test");
//        List<Account> actual = new ArrayList<>();
//        Account newAccount = new Account(null, null, null, newUser);
//        actual.add(newAccount);
//
//        Mockito.when(mockRepository.findByUser(newUser)).thenReturn(Optional.of(actual));
//
//
//
//        //Act
//        Optional<List<Account>> expected = sut.findByUserId(1);
//        //Assert
//
//        assertEquals(expected, actual);
//    }
}
