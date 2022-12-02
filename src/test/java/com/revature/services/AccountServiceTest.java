package com.revature.services;

import com.revature.BankingApplication;
import com.revature.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes= BankingApplication.class)
public class AccountServiceTest {
    @MockBean
    private AccountRepository mockRepository;

    @Autowired
    private AccountService sut;
}
