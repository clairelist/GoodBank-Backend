package com.revature.services;
import com.revature.BankingApplication;
import com.revature.dtos.ResetRequest;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.UpdateRequest;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.CheckRegisterFieldsException;
import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.PasswordUnderAmountException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        reset.setConfirmPassword("newpass");
        reset.setSecurityAnswer("Both produce sour notes");

        User newPass = new User();
        newPass.setId(1);
        newPass.setEmail("test@test.org");
        newPass.setPassword("newpass");

        Mockito.when(mockRepository.findByEmail("test@test.org")).thenReturn(Optional.of(user));
        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(mockRepository.save(newPass)).thenReturn(newPass);
        User actual = sut.updatePassword(reset);
        System.out.println("Expected: " + newPass.getPassword());
        System.out.println("Actual: " + actual.getPassword());

        assertEquals(newPass, actual);
    }

    @Test
    void updatePasswordThrowsPasswordUnderException() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@test.org");
        user.setPassword("originalPass");
        user.setSecurityQuestion("How is a raven like a writing desk?");
        user.setSecurityAnswer("Both produce sour notes");

        ResetRequest reset = new ResetRequest();
        reset.setEmail("test@test.org");
        reset.setPassword("new");
        reset.setConfirmPassword("new");
        reset.setSecurityAnswer("Both produce sour notes");

        assertThrows(PasswordUnderAmountException.class, () -> sut.updatePassword(reset));
    }

    @Test
    void updatePasswordThrowsInvalidInputException() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@test.org");
        user.setPassword("originalPass");
        user.setSecurityQuestion("How is a raven like a writing desk?");
        user.setSecurityAnswer("Both produce sour notes");

        ResetRequest reset = new ResetRequest();
        reset.setEmail("test@test.org");
        reset.setPassword("new");
        reset.setConfirmPassword("news");
        reset.setSecurityAnswer("Both produce sour notes");

        assertThrows(InvalidInputException.class, () -> sut.updatePassword(reset));
    }

    @Test
    void updatePasswordThrowsCheckFieldsException() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@test.org");
        user.setPassword("originalPass");
        user.setSecurityQuestion("How is a raven like a writing desk?");
        user.setSecurityAnswer("Both produce sour notes");

        ResetRequest reset = new ResetRequest();
        reset.setEmail("test@test.org");
        reset.setPassword("");
        reset.setConfirmPassword("");
        reset.setSecurityAnswer("Both produce sour notes");

        assertThrows(CheckRegisterFieldsException.class, () -> sut.updatePassword(reset));
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

    @Test
    void UpdateProfileSuccessful(){
        User stubUser = new User();
        stubUser.setId(1);
        stubUser.setEmail("test@test.org");
        stubUser.setPassword("originalPass");
        stubUser.setSecurityQuestion("How is a raven like a writing desk?");
        stubUser.setSecurityAnswer("Both produce sour notes");
        stubUser.setFirstName("Dave");
        UserDTO expected = new UserDTO(stubUser);
        UpdateRequest req = new UpdateRequest();
        req.setId(stubUser.getId());
        req.setEmail("test@test.org");
        req.setFirstName("Dave");

        Mockito.when(sut.findById(1)).thenReturn(stubUser);
        Mockito.when(mockRepository.save(stubUser)).thenReturn(stubUser);

        UserDTO actual = sut.updateProfile(req);

        assertEquals(expected, actual);
    }

    @Test
    void userSaves() {
        User stubUser = new User();
        stubUser.setId(1);
        stubUser.setEmail("test@test.org");
        stubUser.setPassword("originalPass");
        stubUser.setSecurityQuestion("How is a raven like a writing desk?");
        stubUser.setSecurityAnswer("Both produce sour notes");
        stubUser.setFirstName("Dave");

        Mockito.when(mockRepository.save(stubUser)).thenReturn(stubUser);

        User actual = sut.save(stubUser);

        assertEquals(stubUser, actual);
    }
}

