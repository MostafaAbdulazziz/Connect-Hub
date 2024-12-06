package com.socialnetwork.connecthub;

import com.socialnetwork.connecthub.frontend.swing.view.NewsFeedView;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

public class Main {
    public static void main(String[] args) {
        UserDTO user = new UserDTO();
        user.setUsername("John Doe");
        user.setUserId("12345");
        user.setBio("I am a software engineer");
        user.setProfilePhotoPath("src/test/Screenshot 2024-12-03 011157.png");
        user.setCoverPhotoPath("src/test/Screenshot 2024-12-03 011157.png");
        new NewsFeedView(user);

    }
}
