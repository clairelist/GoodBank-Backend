package com.revature.services;
import com.revature.BankingApplication;
import com.revature.dtos.ResetRequest;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.UserDTO;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(classes= BankingApplication.class)
class UserServiceTest {
    @MockBean
    private UserRepository mockRepository;

    @Autowired
    private UserService sut;
    @Test
    void updatePassword() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@test.org");
        user.setPassword("originalPass");
        user.setSecurityQuestion("How is a raven like a writing desk?");
        user.setSecurityAnswer("Both produce sour notes");

        ResetRequest reset = new ResetRequest();
        reset.setEmail("test@test.org");
        reset.setPassword("newpass");
        reset.setSecurityAnswer("Both produce sour notes");

        User newPass = new User();
        newPass.setId(1);
        newPass.setEmail("test@test.org");
        newPass.setPassword("newpass");

        Mockito.when(mockRepository.findByEmail("test@test.org")).thenReturn(Optional.of(user));
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(mockRepository.save(newPass)).thenReturn(newPass);
        User actual = sut.updatePassword(reset);
        assertEquals(newPass, actual);
        System.out.println("Expected: " + newPass.getPassword());
        System.out.println("Actual: " + actual.getPassword());

    }
    @Test
    void successLogin(){
        LoginRequest creds = new LoginRequest();

        creds.setEmail("test@email.com");
        creds.setPassword("pass");

        User user = new User();

        user.setEmail(creds.getEmail());
        user.setPassword(creds.getPassword());
        UserDTO actual = new UserDTO(user);

        Mockito.when(mockRepository.findByEmailAndPassword(creds.getEmail(), creds.getPassword())).thenReturn(actual);

        UserDTO expected = sut.loginCreds(creds.getEmail(), creds.getPassword());

        assertEquals(expected, actual);

        System.out.println(expected);
        System.out.println(actual);
    }
}

