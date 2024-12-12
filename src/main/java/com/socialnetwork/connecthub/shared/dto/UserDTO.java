package com.socialnetwork.connecthub.shared.dto;

import com.socialnetwork.connecthub.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String username;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String bio;
    private boolean onlineStatus;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.profilePhotoPath = user.getProfilePhotoPath();
        this.coverPhotoPath = user.getCoverPhotoPath();
        this.bio = user.getBio();
        this.onlineStatus = user.isOnlineStatus();
    }

    public UserDTO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public UserDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserDTO setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
        return this;
    }

    public UserDTO setCoverPhotoPath(String coverPhotoPath) {
        this.coverPhotoPath = coverPhotoPath;
        return this;
    }

    public UserDTO setBio(String bio) {
        this.bio = bio;
        return this;
    }

    public UserDTO setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
        return this;
    }
}