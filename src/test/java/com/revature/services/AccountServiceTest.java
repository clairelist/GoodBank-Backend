package com.revature.services;
import com.revature.BankingApplication;
import com.revature.dtos.AccountDTO;
import com.revature.dtos.TransactionDTO;
import com.revature.dtos.TransferDTO;
import com.revature.dtos.UserDTO;
import com.revature.models.*;
import com.revature.repositories.AccountRepository;
import com.revature.repositories.TransactionRepository;
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
class AccountServiceTest {
    @MockBean
    private AccountRepository mockRepository;
    @MockBean
    private TransactionRepository mockTrans;

    @Autowired
    private AccountService sut;
    @Autowired
    private UserService us;
    @Autowired
    private TokenService ts;

    private User stubUser;
    private Account stubAccount;
    private TransactionDTO stubTransactionDTO;
    private Transaction stubTransaction;

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
            new Date(System.currentTimeMillis()),
                "test",
                "test"
        );

        stubAccount = new Account(
                1,
                "testAccount",
                3000.00,
                new Date(System.currentTimeMillis()),
                AccountType.CHECKING,
                stubUser
        );

        stubTransactionDTO = new TransactionDTO(
                1,
                300,
                "test",
                TransactionType.EXPENSE,
                new Date(System.currentTimeMillis()),
                stubAccount,
                null
        );

        stubTransaction = new Transaction(stubTransactionDTO);
    }

    @Test
    void findByIdSendsBackUser() {
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

    @Test
    void upsertTransactionReturnsList(){
        Date now = new Date();
        Transaction updatedTrans = new Transaction(stubTransactionDTO);
        stubAccount.setBalance(stubAccount.getBalance() - updatedTrans.getAmount());
        updatedTrans.setAccount(stubAccount);
        updatedTrans.setCreationDate(now);
        List<Transaction> expected = new ArrayList<>();
        expected.add(updatedTrans);

        Mockito.when(mockRepository.getById(1)).thenReturn(stubAccount);
        Mockito.when(mockRepository.save(stubAccount)).thenReturn(stubAccount);
        Mockito.when(mockTrans.save(updatedTrans)).thenReturn(updatedTrans);
        Mockito.when(mockTrans.findAllByAccountOrderByCreationDateDesc(stubAccount)).thenReturn(expected);

        List<Transaction> actual = sut.upsertTransaction(1, stubTransactionDTO);
        System.out.println("EXPECTED: " + expected);
        System.out.println("ACTUAL: " + actual);

        assertEquals(expected, actual);
    }

    @Test
    void upsertTransferReturnsList(){
        Account stubAccount2 = new Account(
                2,
                "testAccount",
                3000.00,
                new Date(System.currentTimeMillis()),
                AccountType.CHECKING,
                stubUser
        );
        TransferDTO stubTransferDTO = new TransferDTO(stubTransaction);
        stubTransferDTO.setToAccountId(2);
        Date now = new Date();
        Transaction updatedTrans = new Transaction(stubTransactionDTO);
        stubAccount.setBalance(stubAccount.getBalance() - updatedTrans.getAmount());
        updatedTrans.setAccount(stubAccount);
        updatedTrans.setCreationDate(now);
        List<Transaction> expected = new ArrayList<>();
        expected.add(updatedTrans);

        Mockito.when(mockRepository.getById(1)).thenReturn(stubAccount);
        Mockito.when(mockRepository.getById(stubTransferDTO.getToAccountId())).thenReturn(stubAccount2);
        Mockito.when(mockRepository.save(stubAccount)).thenReturn(stubAccount);
        Mockito.when(mockTrans.save(updatedTrans)).thenReturn(updatedTrans);
        Mockito.when(mockTrans.findAllByAccountOrderByCreationDateDesc(stubAccount)).thenReturn(expected);

        List<Transaction> actual = sut.transferTransaction(1, stubTransferDTO);
        System.out.println("EXPECTED: " + expected);
        System.out.println("ACTUAL: " + actual);

        assertEquals(expected, actual);
    }
}
