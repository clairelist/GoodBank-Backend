//package com.revature.services;
//
//import com.revature.models.*;
//import com.revature.repositories.TransactionPageRepository;
//import com.revature.repositories.TransactionRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.doReturn;
//
//public class TransactionPageRepositoryTest {
//    @MockBean
//    private TransactionPageRepository mockRepository;
//
//    @Test
//    public void getFiveTransactionsByPageReturnsFiveTransactions() {
//        Date date = Date.from(Instant.now());
//        User user = new User(1, "testuser@gmail.com", "pass", "Bryan", "Serfozo", "1234 Tampa Ave", "Florida", "Tampa", 57624, UserType.CLIENT, date);
//        Account account = new Account(1, "Primary Checking", 10000.00, date, AccountType.CHECKING, user);
//
//        List<Transaction> transList = new ArrayList<>();
//        transList.add(new Transaction(1, 2500.00, "Payroll Direct Deposit", TransactionType.INCOME, date, account, null));
//        transList.add(new Transaction(2, 500.00, "Rent", TransactionType.EXPENSE, date, account, null));
//        transList.add(new Transaction(3, 50.00, "Gas", TransactionType.EXPENSE, date, account, null));
//        transList.add(new Transaction(4, 2500.00, "Payroll Direct Deposit", TransactionType.INCOME, date, account, null));
//        transList.add(new Transaction(5, 20.00, "Groceries", TransactionType.EXPENSE, date, account, null));
//        transList.add(new Transaction(6, 100.00, "Phone Bill", TransactionType.EXPENSE, date, account, null));
//
//        Pageable pageWithFiveElements = PageRequest.of(0, 5, Sort.by("creationDate").descending());
//
//        doReturn(transList.subList(0, 5)).when(mockRepository).findAllByAccountId(account.getId(), pageWithFiveElements);
//        int actual = mockRepository.findAllByAccountId(account.getId(), pageWithFiveElements).size();
//        int expected = 5;
//
//        assertEquals(expected, actual);
//    }
//
//}
