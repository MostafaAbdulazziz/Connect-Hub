package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.services.ProfileService;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import test.ProfileServiceTest;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProfileView extends View {
    JPanel profilePanel;
    JPanel backgroundPanel;
    RoundedImageLabel profilePhoto;
    JLabel nameLabel;
    JLabel bioLabel;
    UserDTO user;
    ProfileService profileService;
    public ProfileView(UserDTO user) {
        this.user = user;
        profileService = new ProfileServiceTest();
        initialize();



    }


    public void initialize() {
        profilePanel = new JPanel();
        profilePanel.setLayout(null); // Use null layout for precise positioning
        profilePanel.setBackground(new Color(215, 215, 215));
        profilePanel.setBounds(0, 0, getWidth(), getHeight());

        if (user.getCoverPhotoPath() == null || user.getCoverPhotoPath().isEmpty()) {
            user.setCoverPhotoPath("src/test/Screenshot 2024-12-03 011157.png");
        }
        if (user.getProfilePhotoPath() == null || user.getProfilePhotoPath().isEmpty()) {
            user.setProfilePhotoPath("src/test/Screenshot 2024-12-03 011157.png");
        }
        // Set up background panel for the cover photo
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon(user.getCoverPhotoPath()).getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        backgroundPanel.setBounds(0, 0, getWidth(), 200); // Fixed height for cover photo
        profilePanel.add(backgroundPanel); // Add background panel first

        // Set up left panel for profile photo, username, and bio
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);  // Using null layout for positioning
        leftPanel.setBackground(Color.WHITE);  // White background for the left side
        leftPanel.setBounds(0, 200, 300, getHeight());  // Fixed width of 300px and height as full frame

        // Set up profile photo on left panel
        profilePhoto = new RoundedImageLabel(user.getProfilePhotoPath(), 120, 120);
        profilePhoto.setBounds(90, 10, 120, 120); // Positioned within the left panel
        leftPanel.add(profilePhoto); // Add profile photo to left panel

        // Set up user name label under the profile photo
        nameLabel = new JLabel(user.getUsername(), 24, Color.BLACK, Font.BOLD);
        nameLabel.setBounds(90, 140, 200, 20);  // Positioned below profile photo
        leftPanel.add(nameLabel);

        // Set up bio label under the username
        bioLabel = new JLabel(user.getBio(), 18, Color.BLACK, Font.ITALIC);
        bioLabel.setBounds(50, 175, 200, 20);  // Positioned below username
        leftPanel.add(bioLabel);

        // Add left panel to the main profile panel
        profilePanel.add(leftPanel);  // Add the left panel containing profile photo, username, and bio to profilePanel

        // Add profile panel to the main view

        leftPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 6));








        JPanel labelPanel1 = new JPanel();
        labelPanel1.setLayout(new BoxLayout(labelPanel1, BoxLayout.Y_AXIS));
        labelPanel1.setBackground(new Color(215, 215, 215));

        // Add friend labels to the panel
        List<UserDTO> friends = profileService.getUserFriends(user.getUserId());
        for (UserDTO friend : friends) {
            JPanel friendLabel = createFriendLabel(friend);
            labelPanel1.add(friendLabel);
        }

        // Adjust label panel's preferred size dynamically
        int panelHeight = Math.max(1000, friends.size() * 100); // 60px per friend
        labelPanel1.setPreferredSize(new Dimension(300, panelHeight));

        // Add scroll pane
        com.socialnetwork.connecthub.frontend.swing.components.JScrollPane scrollPane = new com.socialnetwork.connecthub.frontend.swing.components.JScrollPane(labelPanel1);
        scrollPane.setBounds(1200, 200, 300, 600); // Position and size
        scrollPane.setBackground(new Color(215, 215, 215));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        profilePanel.add(scrollPane);



        com.socialnetwork.connecthub.frontend.swing.components.JButton homeButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Home", 16, 12);


        homeButton.setBounds(75, 440, 150, 50);

        homeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the user's profile
                new NewsFeedView(user);
                dispose();
            }
        });
        leftPanel.add(homeButton);




        add(profilePanel);
        repaint();
        validate();
    }







    private JPanel createFriendLabel(UserDTO user) {
        // Create the friend panel with a null layout for custom positioning
        JPanel friendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        RoundedImageLabel imageLabel;
        friendPanel.setPreferredSize(new Dimension(400, 60)); // Set fixed size
        friendPanel.setMaximumSize(new Dimension(300, 60));
        friendPanel.setBackground(Color.WHITE);

        // Add rounded image for the friend
        if (user.getProfilePhotoPath() == null || user.getProfilePhotoPath().isEmpty()) {
            imageLabel = new RoundedImageLabel("src/com/socialnetwork/connecthub/resources/pics/friends.png", 50, 50);
            imageLabel.setBounds(0, 0, 40, 40); // Padding: (x, y, width, height)
            friendPanel.add(imageLabel);
        } else {
            imageLabel = new RoundedImageLabel("src/test/Screenshot 2024-12-03 011157.png", 50, 50);
            imageLabel.setBounds(0, 0, 40, 40); // Padding: (x, y, width, height)
            friendPanel.add(imageLabel);
        }


        // Add username text
        javax.swing.JLabel textLabel = new javax.swing.JLabel(user.getUsername());
        textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        textLabel.setFont(new Font("Arial", Font.BOLD, 13));
        textLabel.setForeground(Color.GRAY); // Ensure visible text color
        textLabel.setBounds(60, 10, 130, 30); // Adjust to fit within the panel
        textLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        friendPanel.add(textLabel);

        // Add a border for better visuals
        friendPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        textLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the user's profile
                new ProfileView(user);
                dispose();
            }
        });
        imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the user's profile
                new ProfileView(user);
                dispose();
            }
        });
        friendPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the user's profile
                new ProfileView(user);
                dispose();
            }
        });

        return friendPanel;
    }



}