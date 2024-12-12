package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.backend.interfaces.services.ProfileService;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class EditMyProfileView extends JFrame {

    private JTextArea bioTextArea;
    private JPasswordField newPasswordField;
    private JButton updateProfileButton, updateCoverPhotoButton, updateProfilePhotoButton;
    private RoundedImageLabel profilePhotoLabel;
    private JLabel coverPhotoLabel;
    private boolean profilePhotoChanged = false, coverPhotoChanged = false;
    private String navigationHandlerType = "final";

    // Fixed image sizes for profile and cover photos
    private static final int PROFILE_PHOTO_WIDTH = 100;
    private static final int PROFILE_PHOTO_HEIGHT = 100;
    private static final int COVER_PHOTO_WIDTH = 400;
    private static final int COVER_PHOTO_HEIGHT = 150;
    private String newCoverPhotoPath;
    private String newProfilePhotoPath;

    // Constructor accepting UserDTO instead of individual parameters
    public EditMyProfileView(SocialNetworkAPI socialNetworkAPI, UserDTO userDTO) {

        setTitle("Edit Profile");
        setSize(600, 700); // Increased window size for better fit
        setLayout(new BorderLayout(10, 10));
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Top Panel for Profile and Cover Photos
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));

        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());
        profilePhotoLabel = new RoundedImageLabel(userDTO.getProfilePhotoPath(), PROFILE_PHOTO_WIDTH, PROFILE_PHOTO_HEIGHT);
        profilePanel.add(profilePhotoLabel, BorderLayout.CENTER);
        profilePanel.setBorder(BorderFactory.createTitledBorder("Profile Photo"));

        JPanel coverPanel = new JPanel();
        coverPanel.setLayout(new BorderLayout());
        coverPhotoLabel = new JLabel("", JLabel.CENTER);
        coverPhotoLabel.setIcon(resizeImage(userDTO.getCoverPhotoPath(), COVER_PHOTO_WIDTH, COVER_PHOTO_HEIGHT)); // Set the initial cover photo
        coverPanel.add(coverPhotoLabel, BorderLayout.CENTER);
        coverPanel.setBorder(BorderFactory.createTitledBorder("Cover Photo"));

        topPanel.add(profilePanel, BorderLayout.WEST);
        topPanel.add(coverPanel, BorderLayout.CENTER);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(4, 2, 10, 10));

        middlePanel.add(new JLabel("Bio:"));
        bioTextArea = new JTextArea(userDTO.getBio(), 5, 20); // 5 rows, 20 columns
        bioTextArea.setWrapStyleWord(true);
        bioTextArea.setLineWrap(true);
        JScrollPane bioScrollPane = new JScrollPane(bioTextArea);
        middlePanel.add(bioScrollPane);

        middlePanel.add(new JLabel("New Password:"));
        newPasswordField = new JPasswordField();
        middlePanel.add(newPasswordField);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        updateCoverPhotoButton = new JButton("Update Cover Photo", 14, 12);
        updateProfilePhotoButton = new JButton("Update Profile Photo", 14, 12);
        updateProfileButton = new JButton("Update Profile", 14, 12);

        bottomPanel.add(updateCoverPhotoButton);
        bottomPanel.add(updateProfilePhotoButton);
        bottomPanel.add(updateProfileButton);

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        updateCoverPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newCoverPhotoPath = openFileChooser();
                if (newCoverPhotoPath != null) {
                    coverPhotoLabel.setIcon(resizeImage(newCoverPhotoPath, COVER_PHOTO_WIDTH, COVER_PHOTO_HEIGHT)); // Update cover photo
                    coverPhotoChanged = true;
                }
            }
        });

        updateProfilePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newProfilePhotoPath = openFileChooser();
                if (newProfilePhotoPath != null) {
                    // Update the profile photo with the new rounded image
                    profilePhotoLabel.setImage(newProfilePhotoPath);
                    profilePhotoChanged = true;
                }
            }
        });

        updateProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the bio and new password
                String newBio = bioTextArea.getText();
                String newPassword = new String(newPasswordField.getPassword());

                boolean profileUpdated = false;

                // Call service to update profile (Integrate it with your backend)
                if (profilePhotoChanged) {
                    // Update profile photo using ProfileService
                    socialNetworkAPI.getProfileService().updateProfilePhoto(userDTO.getUserId(), newProfilePhotoPath);
                    profileUpdated = true;
                }


                if (coverPhotoChanged) {
                    // Update cover photo using ProfileService
                    socialNetworkAPI.getProfileService().updateCoverPhoto(userDTO.getUserId(), newCoverPhotoPath);
                    profileUpdated = true;
                }

                if (!newBio.equals(userDTO.getBio())) {
                    // Update bio using ProfileService
                    socialNetworkAPI.getProfileService().updateBio(userDTO.getUserId(), newBio);
                    profileUpdated = true;
                }

                if (!newPassword.isEmpty()) {
                    // Update password using ProfileService
                    socialNetworkAPI.getProfileService().updatePassword(userDTO.getUserId(), newPassword);
                    profileUpdated = true;
                }

                // After update, show a message
                if(profileUpdated) {
                    new Alert("Profile updated successfully!", EditMyProfileView.this);
                    dispose();
//                    NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToMyProfileView(userDTO);
                }
            }
        });
    }

    // Method to resize the image
    private ImageIcon resizeImage(String imagePath, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }

    // File Chooser for selecting image
    private String openFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Photo");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }
}
