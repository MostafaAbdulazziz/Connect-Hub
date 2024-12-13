package com.socialnetwork.connecthub.backend.model;

import com.socialnetwork.connecthub.shared.dto.UserDTO;
import lombok.Data;

import java.util.Date;

@Data
public class FriendRequestNotification extends Notification {
    private UserDTO senderDTO;

    public FriendRequestNotification(UserDTO senderDTO) {
        this.senderDTO = senderDTO;
        this.message = senderDTO.getUsername() + " sent you a friend request.";
        this.read = false;
        this.timestamp = new Date();
    }
}
