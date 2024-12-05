package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import javax.swing.*;
import java.awt.*;

public class MyProfileView extends View {
    JPanel profilePanel;
    JPanel backgroundPanel;
    RoundedImageLabel profilePhoto;
    JLabel nameLabel;
    JLabel bioLabel;
    UserDTO user;
    public MyProfileView(UserDTO user) {
        this.user = user;



    }


    public void initialize() {
        profilePanel = new JPanel();


        if(user.getProfilePhotoPath() == null || user.getProfilePhotoPath().isEmpty()) {
            user.setProfilePhotoPath("src/com/socialnetwork/connecthub/resources/pics/friends.png");
        }
        if(user.getCoverPhotoPath() == null || user.getCoverPhotoPath().isEmpty()) {
            user.setCoverPhotoPath("src/com/socialnetwork/connecthub/resources/pics/friends.png");
        }
        backgroundPanel = new JPanel();
        profilePhoto = new RoundedImageLabel(user.getProfilePhotoPath(), 100, 100);
        profilePhoto.setBounds(30, 30, 100, 100);
        nameLabel = new JLabel(user.getUsername(),12, Color.BLACK, Font.BOLD);
        bioLabel = new JLabel(user.getBio(), 10, Color.BLACK, Font.ITALIC);
        profilePanel.setLayout(null);
        profilePanel.setBackground(Color.GRAY);
        profilePanel.setBounds(0, 0, getWidth(), getHeight());
        add(profilePanel);
        profilePanel.add(profilePhoto);
    }
}
