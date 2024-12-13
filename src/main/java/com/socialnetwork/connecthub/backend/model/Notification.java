package com.socialnetwork.connecthub.backend.model;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Getter
public class Notification {
    protected String message; // Notification message
    protected boolean read; // Mark if the notification is read
    protected Date timestamp;

    public Notification setMessage(String message) {
        this.message = message;
        return this;
    }

    public Notification setRead(boolean read) {
        this.read = read;
        return this;
    }

    public Notification setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
