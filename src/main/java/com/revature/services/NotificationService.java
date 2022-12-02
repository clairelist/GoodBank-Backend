package com.revature.services;

import com.revature.models.Notification;
import com.revature.models.User;
import com.revature.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        //marks a single notification as dismissed, then returns that notification
        Optional<Notification> tableRecord = nr.findById(notificationId);
        if (tableRecord.isPresent()) {
            Notification foundNotification = tableRecord.get();
            foundNotification.setDismissed(true);

            return nr.save(foundNotification);
        }

        // add some kind of exception if notification isn't found
        return null;
    }

    public List<Notification> markListAsSeen(String[] notificationIds) {
        // takes in a list of notification ids, and sets their seen value to true,
        // then returns a new list of the updated notifications
        List<Notification> updated = new ArrayList<>();

        for (String id : notificationIds){
            Optional<Notification> tableRecord = nr.findById(id);
            if (tableRecord.isPresent()) {
                Notification foundNotification = tableRecord.get();
                foundNotification.setSeen(true);

                updated.add(nr.save(foundNotification));
            }
        }

        return updated;
    }

    public List<Notification> getUserNotifications(int userId) {
        User user = us.findById(userId);
        return nr.findByUserAndDismissedFalse(user);
    }
}


