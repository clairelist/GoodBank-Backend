package com.revature.services;

import com.revature.BankingApplication;
import com.revature.models.*;
import com.revature.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
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

        doReturn(transList).when(mockRepository).findAllByAccountOrderByCreationDateDesc(account.getId());

        int expected = transList.size();
        int actual = mockRepository.findAllByAccountOrderByCreationDateDesc(account.getId()).size();

        assertEquals(expected, actual);
    }
}
