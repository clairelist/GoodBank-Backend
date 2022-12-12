package com.revature.services;
import antlr.Token;
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
class LoanServiceTest {
    @MockBean
    private LoanRepository mockRepository;

    @MockBean
    private UserRepository ur;

    @Autowired
    private UserService us;
    @Autowired
    private TokenService ts;

    @Autowired
    private LoanService sut;

    private User mockUser;

    private LoanDTO stubLoan;

    @Autowired
    private TokenService mockTs;

    @Test
    void createLoan() {
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
        Mockito.when(mockRepository.save(loan)).thenReturn(loan);

        LoanDetails actual = sut.createLoan(loanDTO, 1);

        assertEquals(loanDetails.getLoanID(), actual.getLoanID());
    }

    @Test
    void getUserLoans(){
        Date now = new Date();
        User stubUser = new User();
        stubUser.setId(1);

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

        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        loans.add(loan2);

        Mockito.when(us.findById(1)).thenReturn(stubUser);
        Mockito.when(mockRepository.findByUser(stubUser)).thenReturn(loans);

        List<Loan> actual = sut.getUserLoans(stubUser.getId());

        assertEquals(loans, actual);
    }
}
