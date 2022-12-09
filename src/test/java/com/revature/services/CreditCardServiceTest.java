package com.revature.services;

import com.revature.BankingAppTest;
import com.revature.BankingApplication;
import com.revature.models.CreditCard;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.repositories.AccountRepository;
import com.revature.repositories.CreditCardRepository;
import com.revature.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes= BankingApplication.class)
public class CreditCardServiceTest {
    @MockBean
    private CreditCardRepository mockRepository;
    @MockBean
    private AccountRepository mockAccountRepository;
    @MockBean
    private UserRepository mockUserRepository;
    @Autowired
    private CreditCardService sut;
    @Autowired
    private UserService mockUs;
    private User stubUser;
    private CreditCard stubCC;

    @Test
    public void findByUserIdExists() {
        //copy/create stub user
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
        //mock userservice find by id

        //mock credit card for user
        stubCC = new CreditCard(
                1,
                stubUser,
                1234123412341234L,
                387,
                new Date(System.currentTimeMillis()),
                5000,
                4000
        );

        List<CreditCard> actual = new ArrayList<>();
        actual.add(stubCC);

        Mockito.when(mockUs.findById(1)).thenReturn(stubUser);
        Mockito.when(mockRepository.findByUser(Mockito.any(User.class))).thenReturn(actual);

        List<CreditCard> expected = sut.findByUserId(1);

        assertEquals(expected, actual);
    }

}
