package com.revature.services;

import com.revature.BankingApplication;
import com.revature.dtos.LoanDTO;
import com.revature.dtos.LoanDetails;
import com.revature.dtos.UserDTO;
import com.revature.models.Loan;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.repositories.LoanRepository;
import com.revature.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes= BankingApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoanServiceTest {

    @MockBean
    private LoanRepository mockRepository;

    @MockBean
    UserRepository ur;

    @Autowired
    private LoanService sut;

    private User mockUser;

    private LoanDTO stubLoan;

    private TokenService ts;



}
