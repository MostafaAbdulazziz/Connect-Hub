package com.socialnetwork.connecthub.backend.model;

import lombok.Data;

import java.util.Date;

@Data
public class Notification {
    protected String message; // Notification message
    protected boolean read; // Mark if the notification is read
    protected Date timestamp;
}
