package com.revature.services;

import com.revature.BankingApplication;
import com.revature.dtos.NotificationCreationRequest;
import com.revature.models.Notification;
import com.revature.models.NotificationType;
import com.revature.models.User;
import com.revature.models.UserType;
import com.revature.repositories.NotificationRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes= BankingApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificationServiceTest {
    @MockBean
    private NotificationRepository mockNr;
    @MockBean
    private UserService mockUs;
    @Autowired
    private NotificationService ns;
    private User stubUser;
    private NotificationCreationRequest stubRequest;

    @BeforeAll
    void setupTestSuite() {
        // create stub user
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
                "What is your favorite ANIME?",
                "Something cool"
        );

        // create stub request to base notification creation off of
        stubRequest = new NotificationCreationRequest(
                stubUser,
                NotificationType.INFORMATION,
                null,
                "Testing..."
        );
    }

    @Test
    void notificationCreationSuccessful() {
        // create expected result notification
        Notification expected = new Notification(stubRequest);
        expected.setUser(stubUser);

        // when mockNr saves the request, give us back the expected result notification
        Mockito.when(mockNr.save(Mockito.any(Notification.class))).thenReturn(expected);

        // see if NotificationService generates the same based off of the request
        Notification actual = ns.create(stubRequest);

        assertEquals(expected, actual);
    }

    @Test
    void markAsDismissedSuccessful() {
        // create expected result notification
        Notification expected = new Notification(stubRequest);
        expected.setUser(stubUser);
        expected.setDismissed(true);

        // create subject notification
        Notification subject = new Notification(stubRequest);
        subject.setUser(stubUser);
        subject.setDismissed(false); // this is default, but written here for clarity

        // mocking NotificationRepository calls
        Mockito.when(mockNr.findById(subject.getId())).thenReturn(Optional.of(subject));
        Mockito.when(mockNr.save(subject)).thenReturn(expected);

        Notification actual = ns.markAsDismissed(subject.getId());
        assertEquals(expected.getDismissed(), actual.getDismissed());
    }

    @Test
    void markListAsSeenSuccessful() {

        //arrange
        List<Notification> expectedNotifs = new ArrayList<>();
        expectedNotifs.add(new Notification(stubRequest));
        expectedNotifs.add(new Notification(stubRequest));
        expectedNotifs.add(new Notification(stubRequest));

        // we have to extract the ids, and also mark the expected as seen
        String[] ids = new String[3];
        for (int i = 0; i < expectedNotifs.size(); i++){
            ids[i] = expectedNotifs.get(i).getId();
            expectedNotifs.get(i).setSeen(true);
        }

        Notification subjectNotification = new Notification(stubRequest);
        Notification changedNotification = new Notification(stubRequest);
        changedNotification.setSeen(true);

        Mockito.when(mockNr.findById(Mockito.anyString())).thenReturn(Optional.of(subjectNotification));
        Mockito.when(mockNr.save(subjectNotification)).thenReturn(changedNotification);

        List<Notification> actualNotifs = ns.markListAsSeen(ids);

        for (int i = 0; i < actualNotifs.size(); i++){
            assertEquals(
                    expectedNotifs.get(i).getSeen(),
                    actualNotifs.get(i).getSeen()
            );
        }
    }

    @Test
    void getUserNotificationsSuccessful() {
        // arrange
        List<Notification> expectedNotifs = new ArrayList<>();
        expectedNotifs.add(new Notification(stubRequest));
        expectedNotifs.add(new Notification(stubRequest));
        expectedNotifs.add(new Notification(stubRequest));

        for (Notification n: expectedNotifs){
            n.setUser(stubUser);
        }

        // act
        Mockito.when(mockUs.findById(stubUser.getId())).thenReturn(stubUser);
        Mockito.when(mockNr.findByUserAndDismissedFalse(stubUser)).thenReturn(expectedNotifs);

        List<Notification> actualNotifs = ns.getUserNotifications(stubUser.getId());

        // assert
        assertEquals(expectedNotifs, actualNotifs);
    }
}

