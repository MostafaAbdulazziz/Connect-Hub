package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyProfileView extends View {
    JPanel profilePanel;
    JPanel backgroundPanel;
    RoundedImageLabel profilePhoto;
    JLabel nameLabel;
    JLabel bioLabel;
    UserDTO user;
    private SocialNetworkAPI socialNetworkAPI;
    private String navigationHandlerType = "final";

    public MyProfileView(SocialNetworkAPI socialNetworkAPI, UserDTO user) {
        this.user = user;
        this.socialNetworkAPI = socialNetworkAPI;
        profilePanel = new JPanel(null);
        profilePanel.setBackground(new Color(215, 215, 215));
        profilePanel.setLayout(null); // Use null layout for precise positioning
        profilePanel.setBounds(0, 0, getWidth(), getHeight());

        // Set up background panel for the cover photo
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon(user.getCoverPhotoPath()).getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        backgroundPanel.setBounds(0, 0, getWidth(), 200); // Fixed height for a cover photo
        profilePanel.add(backgroundPanel); // Add a background panel first

        // Set up the left panel for a profile photo, username, and bio
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);  // Using null layout for positioning
        leftPanel.setBackground(Color.WHITE);  // White background for the left side
        leftPanel.setBounds(0, 200, 300, getHeight());  // Fixed width of 300px and height as full frame

        // Set up the profile photo on the left panel
        profilePhoto = new RoundedImageLabel(user.getProfilePhotoPath(), 120, 120);
        profilePhoto.setBounds(90, 10, 120, 120); // Positioned within the left panel
        leftPanel.add(profilePhoto); // Add a profile photo to a left panel

        // Set up username label under the profile photo
        nameLabel = new JLabel(user.getUsername(), 18, Color.BLACK, Font.BOLD);
        nameLabel.setBounds(90, 140, 200, 40);  // Positioned below a profile photo
        leftPanel.add(nameLabel);

        // Set up bio label under the username
        bioLabel = new JLabel("<html>" + user.getBio().replace("\n", "<br>") + "</html>", 18, Color.BLACK, Font.ITALIC);
        bioLabel.setBounds(50, 175, 200, 30);  // Positioned below username
        leftPanel.add(bioLabel);

        // Add buttons
        JButton editProfileButton = new JButton("Edit Profile", 16, 12);
        JButton friendManagerButton = new JButton("Friend Manager", 16, 12);
        JButton homeButton = new JButton("Home", 16, 12);


        editProfileButton.setBounds(75, 300, 150, 50);
        friendManagerButton.setBounds(75, 370, 150, 50);
        homeButton.setBounds(75, 440, 150, 50);
        leftPanel.add(editProfileButton);
        leftPanel.add(friendManagerButton);
        leftPanel.add(homeButton);
        editProfileButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open edit profile view
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToEditMyProfileView(user);
//                dispose();
            }
        });

        friendManagerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open friend manager view
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToManageFriendsView(user, MyProfileView.this);
            }
        });

        homeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open news feed view
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToNewsFeedView(user);
                dispose();
            }
        });


        profilePanel.add(leftPanel); // Add a left panel
        leftPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 6));

        // Create a timeline panel and add it inside a JScrollPane
        JPanel timelinePanel = new JPanel(null);
        timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));
        timelinePanel.setBackground(new Color(215, 215, 215));

        List<ContentDTO> contentList = socialNetworkAPI.getContentService().getUserPosts(user.getUserId());
        for (ContentDTO content : socialNetworkAPI.getContentService().getUserPosts(user.getUserId())) {
            JPanel contentLabel = createContentLabel(content);
            timelinePanel.add(contentLabel);
            timelinePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between posts
        }

        // Adjust preferred size based on content
        // Adjust content panel's preferred size dynamically
        int panelHeight = 0; //Math.min(1500, contentList.size() * 800); // 1210 px per content including spacing
        for (ContentDTO content : contentList) {
            if (content.getImagePath() == null || content.getImagePath().isEmpty())
                panelHeight += 300;
            else
                panelHeight += 800;
        }
        timelinePanel.setPreferredSize(new Dimension(800, panelHeight));

        // Create the scroll pane and set its bounds
        JScrollPane scrollPane = new JScrollPane(timelinePanel);
        scrollPane.setBounds(310, 200, 900, 600); // Set the size and position of the scroll pane
        scrollPane.setBackground(new Color(215, 215, 215));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizont

        profilePanel.add(scrollPane);

        // Add profile panel to the main view
        add(profilePanel);
        repaint();
        revalidate();
        setVisible(true);
    }


    private JPanel createContentLabel(ContentDTO content) {
        // Create the content panel with a null layout for custom positioning
        JPanel contentPanel = new JPanel(null);
        contentPanel.setBackground(Color.WHITE);

        // Add rounded image for the author
        RoundedImageLabel authorImageLabel = new RoundedImageLabel(user.getProfilePhotoPath(), 50, 50);
        authorImageLabel.setBounds(10, 10, 50, 50); // Position the image
        contentPanel.add(authorImageLabel);


        // Add author name text
        javax.swing.JLabel authorNameLabel = new javax.swing.JLabel(socialNetworkAPI.getUserAccountService().getUserById(content.getAuthorId()).getUsername());
        authorNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        authorNameLabel.setForeground(Color.BLACK);

        authorNameLabel.setBounds(70, 20, 200, 30); // Adjusted position

        contentPanel.add(authorNameLabel);

        // Add timestamp text
        javax.swing.JLabel timestampLabel = new javax.swing.JLabel(content.getTimestamp().toString());
        timestampLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timestampLabel.setForeground(Color.GRAY);
        timestampLabel.setBounds(650, 20, 170, 30); // Adjusted position
        contentPanel.add(timestampLabel);

        // Add content text
        javax.swing.JLabel contentTextLabel = new javax.swing.JLabel("<html>" + content.getContent().replace("\n", "<br>") + "</html>");
        contentTextLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        contentTextLabel.setForeground(Color.BLACK);

        // Calculate dynamic height based on text length
        int textHeight = (content.getContent().split("\n").length + 1) * 20; // Approx 20px per line
        contentTextLabel.setBounds(50, 80, 750, textHeight);
        contentPanel.add(contentTextLabel);

        // Add content image if image path is valid
        JPanel contentImagePanel = null;
        if (content.getImagePath() != null && !content.getImagePath().isEmpty()) {
            contentImagePanel = new JPanel() {
                private Image image = new ImageIcon(content.getImagePath()).getImage();

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Draw the image scaled to fit the panel
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            };

            // Position the image panel below the text
            contentImagePanel.setBounds(70, textHeight + 100, 700, 400);
            contentPanel.add(contentImagePanel);
        }

        // Add a border for better visuals
        contentPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 5));

        // Set the size of contentPanel dynamically
        if (contentImagePanel != null) {
            contentPanel.setSize(1900, contentImagePanel.getY() + 450);
            contentPanel.setMaximumSize(new Dimension(1900, contentImagePanel.getY() + 450));
        } else {
            contentPanel.setSize(1900, textHeight + 150);
            contentPanel.setMaximumSize(new Dimension(1900, textHeight + 150));
        }

        // Repaint and revalidate to reflect changes
        contentPanel.repaint();
        contentPanel.revalidate();

        return contentPanel;

    }
}
