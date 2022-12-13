package com.revature.services;

import com.revature.BankingApplication;
import com.revature.models.*;
import com.revature.repositories.AccountRepository;
import com.revature.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes= BankingApplication.class)
class TransactionServiceTest {
    @MockBean
    private TransactionRepository mockRepository;
    @MockBean
    private AccountRepository mockAr;
    @Autowired
    private TransactionService sut;


    @Test
    void getTransactionCountgetsCorrectNumber() {
        Date date = Date.from(Instant.now());
        User user = new User(1, "testuser@gmail.com", "pass", "Bryan", "Serfozo", "1234 Tampa Ave", "Florida", "Tampa", 57624, UserType.CLIENT, date, "What is your favorite ANIME?",
                "Something cool");
        Account account = new Account(1, "Primary Checking", 10000.00, date, AccountType.CHECKING, user);

        List<Transaction> transList = new ArrayList<>();
        transList.add(new Transaction(1, 2500.00, "Payroll Direct Deposit", TransactionType.INCOME, date, account, null));
        transList.add(new Transaction(2, 500.00, "Rent", TransactionType.EXPENSE, date, account, null));
        transList.add(new Transaction(3, 50.00, "Gas", TransactionType.EXPENSE, date, account, null));
        transList.add(new Transaction(4, 2500.00, "Payroll Direct Deposit", TransactionType.INCOME, date, account, null));

        Mockito.when(mockAr.getById(1)).thenReturn(account);
        doReturn(transList).when(mockRepository).findAllByAccountOrderByCreationDateDesc(account);

        Object expected = transList.size();
        Object actual = sut.getTransactionCount(1);

        assertEquals(expected, actual);
    }

    @Test
    void getAllTransactionsExists() {
        Date date = Date.from(Instant.now());
        User user = new User(1, "testuser@gmail.com", "pass", "Bryan", "Serfozo", "1234 Tampa Ave", "Florida", "Tampa", 57624, UserType.CLIENT, date, "What is your favorite ANIME?",
                "Something cool");
        Account account = new Account(1, "Primary Checking", 10000.00, date, AccountType.CHECKING, user);

        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction(1, 2500.00, "Payroll Direct Deposit", TransactionType.INCOME, date, account, null));

        Mockito.when(mockAr.getById(1)).thenReturn(account);
        Mockito.when(mockRepository.findAllByAccountOrderByCreationDateDesc(account)).thenReturn(expected);

        List<Transaction> actual = sut.getAllTransactions(1);

        assertEquals(expected, actual);

    }

}
