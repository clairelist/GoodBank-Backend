package com.revature.dtos;

import com.revature.models.NotificationType;
import com.revature.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCreationRequest {
    private User user;
    private NotificationType type;
    private Integer referencesId;
    private String body;

    public NotificationCreationRequest(User user, String body) {
        // 2 arg constructor for basic informational notifications
        this.user = user;
        this.type = NotificationType.INFORMATION;
        this.body = body;
    }
}
