package com.socialnetwork.connecthub.backend.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.socialnetwork.connecthub.backend.service.java.JavaUserAccountService;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Getter
@JsonTypeName("friendRequest")
public class FriendRequestNotification extends Notification {
    private String senderId;

    public FriendRequestNotification() {}

    public FriendRequestNotification(String senderId) {
        this.senderId = senderId;
        this.message = JavaUserAccountService.getInstance().getUserById(senderId).getUsername() + " sent you a friend request.";
        this.read = false;
        this.timestamp = new Date();
    }

    public FriendRequestNotification setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }
}
