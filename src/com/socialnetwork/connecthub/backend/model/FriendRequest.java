package com.socialnetwork.connecthub.backend.model;

import lombok.Data;

@Data
public class FriendRequest {
    private String senderId;
    private String receiverId;
}