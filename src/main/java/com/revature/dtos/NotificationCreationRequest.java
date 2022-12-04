package com.revature.dtos;

import com.revature.models.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCreationRequest {
    private Integer userId;
    private NotificationType type;
    private Integer referencesId;
    private String body;
}
