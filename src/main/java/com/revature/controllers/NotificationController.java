package com.revature.controllers;

import com.revature.models.Notification;
import com.revature.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin(
        origins = {
        "http://localhost:4200",
        "http://localhost:3000",
        "http://good-bank-ui.s3-website-us-west-2.amazonaws.com"
        },
        allowCredentials = "true",
        allowedHeaders = "*"
)
public class NotificationController {

    private NotificationService ns;

    @Autowired
    public NotificationController(NotificationService ns) {
        this.ns = ns;
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable("user_id") String userId){
        List<Notification> notifs = ns.getUserNotifications(Integer.parseInt(userId));

        return ResponseEntity.ok(notifs);
    }
}
