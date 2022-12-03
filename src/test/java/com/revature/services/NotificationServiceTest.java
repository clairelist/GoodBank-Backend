package com.revature.services;

import com.revature.BankingApplication;
import com.revature.dtos.NotificationCreationRequest;
import com.revature.models.Notification;
import com.revature.models.NotificationType;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.repositories.NotificationRepository;
import com.revature.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes= BankingApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificationServiceTest {
    @MockBean
    private NotificationRepository mockNr;

    @MockBean
    private UserRepository mockUr;

    @Autowired
    private NotificationService ns;

    private User stubUser;

    @BeforeAll
    void setupTests(){
        stubUser = new User();
        stubUser.setId(1);
        stubUser.setUserType(UserType.CLIENT);
        stubUser.setFirstName("Lesly");
        stubUser.setLastName("Gonzalez");
        stubUser.setEmail("lilmissgogetta@revature.com");
        stubUser.setPassword("pass");
        stubUser.setAddress("1234 Revature Lane");
        stubUser.setCity("Cleveland");
        stubUser.setState("Ohio");
        stubUser.setZip(44102);
        stubUser.setCreationDate(new Date(System.currentTimeMillis()));
    }

    @Test
    void notificationCreationSuccessful(){
        //arrange
        NotificationCreationRequest request = new NotificationCreationRequest();
        request.setUserId(stubUser.getId());
        request.setType(NotificationType.WARNING);
        request.setBody("Testing...");

        Notification expected = new Notification(request);
        expected.setUser(stubUser);
        //act

        Mockito.when(mockNr.save(Mockito.any(Notification.class))).thenReturn(expected);

        Notification actual = ns.save(request);
        //assert
        assertEquals(expected, actual);
    }
}

