package com.socialnetwork.connecthub.backend.model;

import lombok.Data;

@Data
public class FriendRequest {
    private String senderId;
    private String receiverId;

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}