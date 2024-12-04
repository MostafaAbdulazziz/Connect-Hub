package com.socialnetwork.connecthub.backend.interfaces.services;

import com.socialnetwork.connecthub.shared.dto.UserDTO;

import java.util.List;

public interface ProfileService {
    void updateProfilePhoto(String userId, String photoPath);
    void updateCoverPhoto(String userId, String photoPath);
    void updateBio(String userId, String bio);
    void updatePassword(String userId, String newPassword);
    List<UserDTO> getUserFriends(String userId);
}