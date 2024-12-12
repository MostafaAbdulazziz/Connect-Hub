package com.socialnetwork.connecthub.backend.model;

import lombok.Data;

@Data
public class Block {
    private String blockingUserId;
    private String blockedUserId;
}
