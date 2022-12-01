package com.revature.services;

import com.revature.models.Notification;
import com.revature.models.User;
import com.revature.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private NotificationRepository nr;
    private UserService us;

    @Autowired
    public NotificationService(NotificationRepository nr, UserService us) {
        this.nr = nr;
        this.us = us;
    }

    public Notification save(Notification n) {
        return nr.save(n);
    }

    public Notification markAsDismissed(String notificationId) {
        Optional<Notification> tableRecord = nr.findById(notificationId);
        if (tableRecord.isPresent()) {
            Notification foundNotification = tableRecord.get();
            foundNotification.setDismissed(true);

            return nr.save(foundNotification);
        }

        // add some kind of exception if notification isn't found
        return null;
    }

    public List<Notification> getUserNotifications(int userId) {
        User user = us.findById(userId);
        return nr.findByUserAndDismissedFalse(user);
    }
}


