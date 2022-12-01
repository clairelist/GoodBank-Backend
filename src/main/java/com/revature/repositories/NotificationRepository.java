package com.revature.repositories;

import com.revature.models.Notification;
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findByUser(User user);

    List<Notification> findByUserAndDismissedFalse(User user);

    List<Notification> findByUserAndDismissedFalseOrderByTimeAsc(User user);

}
