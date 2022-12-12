package com.revature.controllers;

import com.revature.annotations.Secured;
import com.revature.dtos.NotificationCreationRequest;
import com.revature.models.Notification;
import com.revature.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://good-bank-ui.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true", exposedHeaders = "Authorization")
public class NotificationController {

    private NotificationService ns;

    @Autowired
    public NotificationController(NotificationService ns) {
        this.ns = ns;
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody NotificationCreationRequest request){
        Notification savedNotification = ns.create(request);
        return ResponseEntity.ok(savedNotification);
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @GetMapping("/{user_id}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable("user_id") int userId){
        List<Notification> notifs = ns.getUserNotifications(userId);

        return ResponseEntity.ok(notifs);
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PatchMapping("/dismiss/{user_id}/{notification_id}")
    public ResponseEntity<List<Notification>> dismissUserNotification(
        @PathVariable("user_id") int userId,
        @PathVariable("notification_id") String notificationId
    ){
        if (ns.markAsDismissed(notificationId) != null){
            List<Notification> notifs = ns.getUserNotifications(userId);

            return ResponseEntity.ok(notifs);
        }

        return ResponseEntity.notFound().build();
    }

    @Secured(rolesAllowed = { "ADMIN", "CLIENT" })
    @PatchMapping("/seen")
    public ResponseEntity<List<Notification>> setNotificationsSeen(@RequestBody String[] ids){
        List<Notification> updated = ns.markListAsSeen(ids);

        return ResponseEntity.ok(updated);
    }
}
