package com.revature.services;

import com.revature.BankingApplication;
import com.revature.dtos.CreditCardTransactionDTO;
import com.revature.models.*;
import com.revature.repositories.*;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes= BankingApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreditCardServiceTest {
    @MockBean
    private CreditCardRepository mockRepository;
    @MockBean
    private AccountRepository mockAccountRepository;
    @MockBean
    private UserRepository mockUserRepository;
    @MockBean
    private TransactionRepository mockTransactionRepository;
    @MockBean
    private CreditCardTransactionRepository mockCreditCardTransactionRepository;
    @Autowired
    private CreditCardService sut;
    @Autowired
    private UserService mockUs;
    private User stubUser;
    private CreditCard stubCC;
    private CreditCardTransaction stubCCTransaction;
    private CreditCardTransactionDTO stubCCTransactionDTO;
    private Account stubAccount;

    @BeforeAll
    void setupTestSuit() {
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

        stubCC = new CreditCard(
                5,
                stubUser,
                1234123412341234L,
                222,
                new Date(System.currentTimeMillis()),
                10000,
                5000,
                Status.PENDING
        );

        stubAccount = new Account(
                3,
                "checking",
                10000.0,
                new Date(System.currentTimeMillis()),
                AccountType.CHECKING,
                stubUser
        );
    }
    @Test
    void findByUserIdExists() {
        stubCC = new CreditCard(
                1,
                stubUser,
                1234123412341234L,
                387,
                new Date(System.currentTimeMillis()),
                5000,
                4000,
                Status.PENDING
        );

        List<CreditCard> actual = new ArrayList<>();
        actual.add(stubCC);
        Mockito.when(mockUs.findById(1)).thenReturn(stubUser);
        Mockito.when(mockRepository.findByUser(Mockito.any(User.class))).thenReturn(actual);
        List<CreditCard> expected = sut.findByUserId(1);

        assertEquals(expected, actual);
    }

    @Test
    void makeCreditCardPaymentWorks() {
        stubCCTransactionDTO = new CreditCardTransactionDTO(
            10,
            500,
            "payment",
            5,
            3
        );
        stubCCTransaction = new CreditCardTransaction(
                10,
                500,
                "Payment from Account " + stubAccount.getName(),
                new Date(System.currentTimeMillis()),
                CreditCardTransactionType.PAYMENT,
                stubCC,
                stubAccount
        );

        List<CreditCardTransaction> actual = new ArrayList<>();
        actual.add(stubCCTransaction);
        Mockito.when(mockRepository.getById(stubCCTransactionDTO.getCreditCardId())).thenReturn(stubCC);
        Mockito.when(mockAccountRepository.getById(stubCCTransactionDTO.getAccountId())).thenReturn(stubAccount);
        Mockito.when(mockUserRepository.getById(1)).thenReturn(stubUser);
        Mockito.when(mockCreditCardTransactionRepository.findAllByCreditCardOrderByCreationDateDesc(stubCC)).thenReturn(actual);
        Double expected = sut.makeCreditCardPayment(1, stubCCTransactionDTO);

        assertEquals(expected, stubAccount.getBalance());
    }
}
