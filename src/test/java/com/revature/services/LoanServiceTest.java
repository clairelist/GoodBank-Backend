package com.revature.services;

import com.revature.BankingApplication;
import com.revature.dtos.LoanDTO;
import com.revature.dtos.LoanDetails;
import com.revature.dtos.UserDTO;
import com.revature.models.Loan;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.repositories.LoanRepository;
import com.revature.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

import static com.revature.models.Status.APPROVED;
import static com.revature.models.Status.PENDING;
import static com.revature.models.UserType.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = BankingApplication.class)
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

    @Autowired
    private TokenService mockTs;

//    @BeforeAll
//    void setUpTestSuite() {
//        mockUser = new User(
//                1,
//                "lilmissgogetta@revature.com",
//                "pass",
//                "Lesly",
//                "Gonzalez",
//                "1234 Revature Lane",
//                "Cleveland",
//                "Ohio",
//                44102,
//                UserType.CLIENT,
//                new Date(System.currentTimeMillis())
//        );
//
//        stubLoan = new LoanDTO(
//                mockUser,
//                "testing loans",
//                250
//        );
//    }

    @Test
    public void createLoan() {
        Date now = new Date();
        User stubUser = new User();
        stubUser.setId(1);
        LoanDTO loanDTO = new LoanDTO();
//        loanDTO.setLoanID(1);
        loanDTO.setReason("testing create loan function");
        loanDTO.setInitialAmount(2500);
        loanDTO.setUserId(stubUser);

        Loan loan = new Loan();
        loan.setId(1);
        loan.setCreationDate(now);
        loan.setStatus(Status.PENDING);
        loan.setUser(stubUser);
        loan.setBalance(10000);
        loan.setReason("testing create loan function");
        loan.setInitialAmount(10000);

        LoanDetails loanDetails = new LoanDetails();
        loanDetails.setLoanID(0);
        loanDetails.setUserId(stubUser.getId());
        loanDetails.setReason("testing create loan function");
        loanDetails.setInitialAmount(2500);
        loanDetails.setBalance(2500);
        loanDetails.setCreationDate(now);
        loanDetails.setStatus(String.valueOf(Status.PENDING));


        Mockito.when(ur.getById(1)).thenReturn(stubUser);

//        LoanDTO expected = new LoanDTO();
//        expected.setUserId(stubUser);
//        expected.setReason("testing create loan function");
//        expected.setInitialAmount(25000);


        LoanDetails actual = sut.createLoan(loanDTO, 1);
//        actual.setLoanID(1);

        assertEquals(loanDetails.getLoanID(), actual.getLoanID());
        System.out.println("EXPECTED: " + loanDTO);
        System.out.println("ACTUAL: " + actual);

    }


}
