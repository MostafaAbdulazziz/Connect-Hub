package com.socialnetwork.connecthub.backend.model;

import lombok.Data;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Date;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FriendRequestNotification.class, name = "friendRequest"),
        @JsonSubTypes.Type(value = NewPostNotification.class, name = "newPost"),
        @JsonSubTypes.Type(value = GroupNotification.class, name = "group")
})
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
