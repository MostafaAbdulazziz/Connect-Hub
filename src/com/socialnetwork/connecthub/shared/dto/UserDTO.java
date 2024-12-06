package com.socialnetwork.connecthub.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String username;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String bio;
    private boolean onlineStatus;
}