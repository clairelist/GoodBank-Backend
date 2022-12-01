package com.revature.dtos;

import com.revature.models.Notification;
import com.revature.models.NotificationType;
import com.revature.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private String id;
    private User user;
    private NotificationType type;
    private Integer referencesId;
    private String body;
    private Boolean dismissed;
    private Boolean seen;
    private Timestamp time;

    public NotificationDTO(Notification notification){
        this.id = notification.getId();
        this.user = notification.getUser();
        this.type = notification.getType();
        this.referencesId = notification.getReferencesId();
        this.body = notification.getBody();
        this.dismissed = notification.getDismissed();
        this.seen = notification.getSeen();
        this.time = notification.getTime();
    }
}
