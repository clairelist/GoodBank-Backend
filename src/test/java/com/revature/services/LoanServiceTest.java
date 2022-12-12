package com.revature.services;
import com.revature.BankingApplication;
import com.revature.dtos.LoanDTO;
import com.revature.dtos.LoanDetails;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = BankingApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoanServiceTest {
    @MockBean
    private LoanRepository mockRepository;

    @MockBean
    UserRepository ur;

    @MockBean
    UserService us;

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


        LoanDetails actual = sut.createLoan(loanDTO, 1);

        assertEquals(loanDetails.getLoanID(), actual.getLoanID());
        System.out.println("EXPECTED: " + loanDTO);
        System.out.println("ACTUAL: " + actual);

    }

    @Test
    public void getUserLoans(){
        Date now = new Date();
        User stubUser = new User();
        stubUser.setId(1);
        User stubUser2 = new User();
        stubUser2.setId(2);

        Loan loan = new Loan();
        loan.setId(1);
        loan.setCreationDate(now);
        loan.setStatus(Status.PENDING);
        loan.setUser(stubUser);
        loan.setBalance(10000);
        loan.setReason("testing create loan function");
        loan.setInitialAmount(10000);

        Loan loan2 = new Loan();
        loan2.setId(2);
        loan2.setCreationDate(now);
        loan2.setStatus(Status.PENDING);
        loan2.setUser(stubUser);
        loan2.setBalance(10000);
        loan2.setReason("testing getting user loans");
        loan2.setInitialAmount(10000);

        LoanDTO expected = new LoanDTO();
        expected.setUserId(stubUser);
        expected.setReason("testing getting loans funct");
        expected.setInitialAmount(2500);

        LoanDTO expected2 = new LoanDTO();
        expected2.setUserId(stubUser);
        expected2.setReason("testing getting loans funct");
        expected2.setInitialAmount(2500);



        List<LoanDTO> loans = new ArrayList<>();
        loans.add(expected);
        loans.add(expected2);


        Mockito.when(us.findById(1)).thenReturn(stubUser);

        List<Loan> actual = sut.getUserLoans(stubUser.getId());


        assertEquals(loans, actual);
        System.out.println("EXPECTED: " + loans);
        System.out.println("ACTUAL: " + actual);

    }
}
