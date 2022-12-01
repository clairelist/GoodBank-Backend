package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    private String id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column
    private Integer referencesId;

    @Column
    private String body;

    @Column(nullable=false)
    private Boolean dismissed;

    @Column(nullable=false)
    private Boolean seen;

    @Column
    private Timestamp time;

    public Notification() {
        this.id = String.valueOf(UUID.randomUUID());
        this.time = new Timestamp(System.currentTimeMillis());
        this.dismissed = false;
        this.seen = false;
    }

    public Notification(User user, NotificationType type, Integer referencesId, String body) {
        this.id = String.valueOf(UUID.randomUUID());
        this.user = user;
        this.type = type;
        this.referencesId = referencesId;
        this.body = body;
        this.time = new Timestamp(System.currentTimeMillis());
        this.dismissed = false;
        this.seen = false;
    }
}
