package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import javax.swing.*;
import java.awt.*;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;
import java.util.List;

public class SearchResultsView extends JFrame {

    private JPanel userListPanel;
    private JPanel groupListPanel;
    private JScrollPane userScrollPane;
    private JScrollPane groupScrollPane;
    private List<UserDTO> friends;
    private List<GroupDTO> groups;
    private SocialNetworkAPI socialNetworkAPI;
    UserDTO user;


    public SearchResultsView(SocialNetworkAPI socialNetworkAPI, UserDTO user, List<UserDTO> friends, List<GroupDTO> groups) {
        this.socialNetworkAPI = socialNetworkAPI;
        this.friends = friends;
        this.groups = groups;
        this.user = user;

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
        usersPanel.add(createUserListPanel(), BorderLayout.CENTER);
        tabbedPane.addTab("Users", usersPanel);

        // Groups Tab
        JPanel groupsPanel = new JPanel();
        groupsPanel.setLayout(new BorderLayout());
        groupListPanel = new JPanel();
        groupListPanel.setLayout(new BoxLayout(groupListPanel, BoxLayout.Y_AXIS));
        groupsPanel.add(createGroupListPanel(), BorderLayout.CENTER);
        tabbedPane.addTab("Groups", groupsPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Panel to hold all user labels (with rounded images and action buttons)
    private JScrollPane createUserListPanel() {
        userListPanel.setBackground(Color.WHITE);

        for (UserDTO friend : friends) {
            userListPanel.add(createFriendLabel(friend));  // Create each user label dynamically
        }

        userScrollPane = new JScrollPane(userListPanel);
        userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        userScrollPane.setPreferredSize(new Dimension(600, 400)); // Set preferred size for the scroll pane

        return userScrollPane;
    }

    // Panel to hold all group labels (with group image and action buttons)
    private JScrollPane createGroupListPanel() {
        groupListPanel.setBackground(Color.WHITE);

        for (GroupDTO group : groups) {
            groupListPanel.add(createGroupLabel(group));  // Create each group label dynamically
        }

        groupScrollPane = new JScrollPane(groupListPanel);
        groupScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        groupScrollPane.setPreferredSize(new Dimension(600, 400)); // Set preferred size for the scroll pane

        return groupScrollPane;
    }

    // Method to create the user label with their photo, name, and button
    private JPanel createFriendLabel(UserDTO friend) {
        JPanel friendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        friendPanel.setPreferredSize(new Dimension(400, 80)); // Adjust size
        friendPanel.setMaximumSize(new Dimension(400, 80));
        friendPanel.setBackground(Color.WHITE);

        // Add profile photo (rounded)
        RoundedImageLabel imageLabel = new RoundedImageLabel(friend.getProfilePhotoPath(), 50, 50);
        imageLabel.addActionListener(e -> NavigationHandlerFactory.getNavigationHandler("final").goToProfileView(friend,user));  // Open user profile on photo click
        friendPanel.add(imageLabel);

        // Add username text with action listener to open profile
        JLabel textLabel = new JLabel(friend.getUsername(), 12, Color.GRAY, Font.BOLD);
        textLabel.setFont(new Font("Arial", Font.BOLD, 13));
        textLabel.setForeground(Color.GRAY);
        textLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NavigationHandlerFactory.getNavigationHandler("final").goToProfileView(friend,user); // Open user profile on name click
            }
        });
        friendPanel.add(textLabel);

        // Add the action button (Send Friend Request)
        JButton actionButton = new JButton("Send Friend Request", 10, 12);
        actionButton.setBackground(new Color(66, 103, 178));
        actionButton.setForeground(Color.WHITE);
        actionButton.setFont(new Font("Arial", Font.PLAIN, 12));
        actionButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        actionButton.addActionListener(e -> sendFriendRequest(friend));
        friendPanel.add(actionButton);

        friendPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));

        return friendPanel;
    }

    // Method to create the group label with their image, name, and button
    private JPanel createGroupLabel(GroupDTO group) {
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        groupPanel.setPreferredSize(new Dimension(400, 80)); // Adjust size
        groupPanel.setMaximumSize(new Dimension(400, 80));
        groupPanel.setBackground(Color.WHITE);

        // Add group image (rounded)
        RoundedImageLabel imageLabel = new RoundedImageLabel(group.getIconPhotoPath(), 50, 50);
        imageLabel.addActionListener(e -> openGroupDetails(group.getGroupId()));  // Open group details on image click
        groupPanel.add(imageLabel);

        // Add group name text with action listener to open group details
        JLabel textLabel = new JLabel(group.getName(), 12, Color.GRAY, Font.BOLD);
        textLabel.setFont(new Font("Arial", Font.BOLD, 13));
        textLabel.setForeground(Color.GRAY);
        textLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openGroupDetails(group.getGroupId()); // Open group details on name click
            }
        });
        groupPanel.add(textLabel);

        // Add the action button (Join Group)
        JButton actionButton = new JButton("Join Group", 10, 12);
        actionButton.setBackground(new Color(66, 103, 178));
        actionButton.setForeground(Color.WHITE);
        actionButton.setFont(new Font("Arial", Font.PLAIN, 12));
        actionButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        actionButton.addActionListener(e -> joinGroup(group));
        groupPanel.add(actionButton);

        groupPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));

        return groupPanel;
    }

    // Send friend request
    private void sendFriendRequest(UserDTO friend) {
        try {
            socialNetworkAPI.getFriendService().sendFriendRequest(user.getUserId(), friend.getUserId());
            JOptionPane.showMessageDialog(this, "Friend request sent to " + user.getUsername());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error sending friend request: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Join a group
    private void joinGroup(GroupDTO group) {
        try {
            socialNetworkAPI.getGroupService().requestToJoin(group.getGroupId(), user.getUserId());
            JOptionPane.showMessageDialog(this, "Request to join " + group.getName() + " sent.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error joining group: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void openGroupDetails(String groupId) {
        // Code to open group details (e.g., new GroupDetailsView(groupId))
    }
}
