package com.socialnetwork.connecthub.shared.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String userId;
    private String username;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String bio;
    private boolean onlineStatus;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCoverPhotoPath() {
        return coverPhotoPath;
    }

    public void setCoverPhotoPath(String coverPhotoPath) {
        this.coverPhotoPath = coverPhotoPath;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}