package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.backend.interfaces.services.ProfileService;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import test.ProfileServiceTest;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class EditProfile extends JFrame {

    private JTextArea bioTextArea;
    private JPasswordField newPasswordField;
    private JButton updateProfileButton, updateCoverPhotoButton, updateProfilePhotoButton;
    private RoundedImageLabel profilePhotoLabel;
    private JLabel coverPhotoLabel;
    private UserDTO userDTO;
    private boolean profilePhotoChanged = false, coverPhotoChanged = false;

    // Fixed image sizes for profile and cover photos
    private static final int PROFILE_PHOTO_WIDTH = 100;
    private static final int PROFILE_PHOTO_HEIGHT = 100;
    private static final int COVER_PHOTO_WIDTH = 400;
    private static final int COVER_PHOTO_HEIGHT = 150;
    private String newCoverPhotoPath;
    String newProfilePhotoPath;

    private ProfileService profileService; // Injected ProfileService

    // Constructor accepting UserDTO instead of individual parameters
    public EditProfile(UserDTO userDTO, ProfileService profileService) {
        this.userDTO = userDTO;
        this.profileService = profileService;

        setTitle("Edit Profile");
        setSize(600, 700); // Increased window size for better fit
        setLayout(new BorderLayout(10, 10));

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

                // Call service to update profile (Integrate it with your backend)
                if (profilePhotoChanged)
                    // Update profile photo using ProfileService
                    profileService.updateProfilePhoto(userDTO.getUserId(), newProfilePhotoPath);


                if (coverPhotoChanged)
                    // Update cover photo using ProfileService
                    profileService.updateCoverPhoto(userDTO.getUserId(), newCoverPhotoPath);


                if (!newBio.equals(userDTO.getBio()))
                    // Update bio using ProfileService
                    profileService.updateBio(userDTO.getUserId(), newBio);


                if (!newPassword.isEmpty())
                    // Update password using ProfileService
                    profileService.updatePassword(userDTO.getUserId(), newPassword);

                // After update, show a message
                new Alert("Profile updated successfully!", EditProfile.this);
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
