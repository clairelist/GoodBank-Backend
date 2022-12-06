//package com.revature.services;
//import com.revature.BankingApplication;
//import com.revature.models.Account;
//import com.revature.models.User;
//import com.revature.models.UserType;
//import com.revature.repositories.AccountRepository;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.BeforeAll;
//import com.revature.models.*;
//import com.revature.repositories.TransactionRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.event.annotation.BeforeTestMethod;
//
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.doReturn;
//
//@SpringBootTest(classes= BankingApplication.class)
//public class AccountServiceTest {
//    @MockBean
//    private TransactionRepository mockRepository;
//
//    @Autowired
//    private AccountService sut;
//    private UserService us;
//
//}
//    @Test
//    public void findByIdSendsBackUser(){
//        //Arrange
//        User newUser = new User();
//        newUser.setId(1);
//        newUser.setEmail("test@gmail.com");
//        newUser.setPassword("test");
//
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
