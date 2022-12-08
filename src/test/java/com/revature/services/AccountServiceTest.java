package com.revature.services;
import com.revature.BankingApplication;
import com.revature.dtos.AccountDTO;
import com.revature.dtos.TransactionDTO;
import com.revature.dtos.UserDTO;
import com.revature.models.*;
import com.revature.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes= BankingApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountServiceTest {
    @MockBean
    private AccountRepository mockRepository;

    @Autowired
    private AccountService sut;
    @Autowired
    private UserService us;
    @Autowired
    private TokenService ts;

    private User stubUser;
    private Account stubAccount;
    private TransactionDTO stubTransaction;

    @BeforeAll
    void setupTestSuite() {
        // create stub user
        stubUser = new User(
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
            new Date(System.currentTimeMillis())
        );

        stubAccount = new Account(
                1,
                "testAccount",
                300.00,
                new Date(System.currentTimeMillis()),
                AccountType.CHECKING,
                stubUser
        );

        stubTransaction = new TransactionDTO(
                1,
                300,
                "test",
                TransactionType.EXPENSE,
                new Date(System.currentTimeMillis()),
                stubAccount,
                null
        );
    }

    @Test
    public void findByIdSendsBackUser() {
        //Arrange
        List<Account> actual = new ArrayList<>();
        Account newAccount = new Account(null, null, null, stubUser);
        actual.add(newAccount);

        Mockito.when(mockRepository.findByUser(Mockito.any(User.class))).thenReturn(actual);


        //Act
        List<Account> expected = sut.findByUserId(1);
        //Assert

        assertEquals(expected, actual);
    }

}
