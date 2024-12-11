package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SearchResultsView extends JFrame {

    private JPanel userListPanel;
    private JPanel groupListPanel;
    private JScrollPane userScrollPane;
    private JScrollPane groupScrollPane;

    public SearchResultsView(/*List<UserDTO> users, List<GroupDTO> groups*/) {

        setTitle("Connect Hub - Filtered Results");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Users Tab
        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(new BorderLayout());
        userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
        //usersPanel.add(createUserListPanel(users), BorderLayout.CENTER);
        tabbedPane.addTab("Users", usersPanel);

        // Groups Tab
        JPanel groupsPanel = new JPanel();
        groupsPanel.setLayout(new BorderLayout());
        groupListPanel = new JPanel();
        groupListPanel.setLayout(new BoxLayout(groupListPanel, BoxLayout.Y_AXIS));
        //groupsPanel.add(createGroupListPanel(groups), BorderLayout.CENTER);
        tabbedPane.addTab("Groups", groupsPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Panel to hold all user labels (with rounded images and action buttons)
    private JScrollPane createUserListPanel(/*List<UserDTO> users*/) {
        userListPanel.setBackground(Color.WHITE);

       /* for (UserDTO user : users) {
            userListPanel.add(createFriendLabel(user));  // Create each user label dynamically
        }*/

        userScrollPane = new JScrollPane(userListPanel);
        userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        userScrollPane.setPreferredSize(new Dimension(600, 400)); // Set preferred size for the scroll pane

        return userScrollPane;
    }

    // Panel to hold all group labels (with group image and action buttons)
    private JScrollPane createGroupListPanel(/*List<GroupDTO> groups*/) {
        groupListPanel.setBackground(Color.WHITE);
/*
        for (GroupDTO group : groups) {
            groupListPanel.add(createGroupLabel(group));  // Create each group label dynamically
        }*/

        groupScrollPane = new JScrollPane(groupListPanel);
        groupScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        groupScrollPane.setPreferredSize(new Dimension(600, 400)); // Set preferred size for the scroll pane

        return groupScrollPane;
    }

    // Method to create the user label with their photo, name, and button
    private JPanel createFriendLabel(UserDTO user) {
        JPanel friendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        friendPanel.setPreferredSize(new Dimension(400, 80)); // Adjust size
        friendPanel.setMaximumSize(new Dimension(400, 80));
        friendPanel.setBackground(Color.WHITE);

        // Add profile photo (rounded)
        RoundedImageLabel imageLabel = new RoundedImageLabel(user.getProfilePhotoPath(), 50, 50);
        imageLabel.addActionListener(e -> openUserProfile(user.getUsername()));  // Open user profile on photo click
        friendPanel.add(imageLabel);

        // Add username text with action listener to open profile
        JLabel textLabel = new JLabel(user.getUsername(), 12, Color.GRAY, Font.BOLD);
        textLabel.setFont(new Font("Arial", Font.BOLD, 13));
        textLabel.setForeground(Color.GRAY);
        textLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openUserProfile(user.getUsername()); // Open user profile on name click
            }
        });
        friendPanel.add(textLabel);

        // Add the action button (Send Friend Request / Requested / Unfriend)
        JButton actionButton = new JButton("Send Friend Request", 10, 12);
        actionButton.setBackground(new Color(66, 103, 178));
        actionButton.setForeground(Color.WHITE);
        actionButton.setFont(new Font("Arial", Font.PLAIN, 12));
        actionButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        actionButton.addActionListener(e -> {

        });

        friendPanel.add(actionButton);

        friendPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));

        return friendPanel;
    }

    // Method to create the group label with their image, name, and button
    private JPanel createGroupLabel(/*GroupDTO group*/) {
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        groupPanel.setPreferredSize(new Dimension(400, 80)); // Adjust size
        groupPanel.setMaximumSize(new Dimension(400, 80));
        groupPanel.setBackground(Color.WHITE);

        // Add group image (rounded)
        RoundedImageLabel imageLabel = new RoundedImageLabel("group image", 50, 50);
        imageLabel.addActionListener(e -> openGroupDetails("group name"));  // Open group details on image click
        groupPanel.add(imageLabel);

        // Add group name text with action listener to open group details
        JLabel textLabel = new JLabel("group name", 12, Color.GRAY, Font.BOLD);
        textLabel.setFont(new Font("Arial", Font.BOLD, 13));
        textLabel.setForeground(Color.GRAY);
        textLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            }
        });
        groupPanel.add(textLabel);

        // Add the action button (Join Group / Leave)
        JButton actionButton = new JButton("Join Group", 10, 12);
        actionButton.setBackground(new Color(66, 103, 178));
        actionButton.setForeground(Color.WHITE);
        actionButton.setFont(new Font("Arial", Font.PLAIN, 12));
        actionButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        actionButton.addActionListener(e -> {
        });
        groupPanel.add(actionButton);

        groupPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));

        return groupPanel;
    }

    // Placeholder methods to handle actions
    private void openUserProfile(String username) {
        // Code to open user profile
    }

    private void openGroupDetails(String groupName) {
        // Code to open group details
    }
}