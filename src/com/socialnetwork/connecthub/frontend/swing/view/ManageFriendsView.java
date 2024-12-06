package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.backend.interfaces.services.UserAccountService;
import com.socialnetwork.connecthub.backend.model.FriendRequest;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import com.socialnetwork.connecthub.backend.interfaces.services.FriendService;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ManageFriendsView extends JFrame {
    private FriendService friendService;
    private JTabbedPane tabbedPane;
    private String currentUserId;
    private UserAccountService userAccountService;
    private String navigationHandlerType = "final";

    public ManageFriendsView(SocialNetworkAPI socialNetworkAPI, UserDTO userDTO) {
        this.userAccountService = userAccountService;
        this.friendService = friendService;
        this.currentUserId = currentUserId;
        setupUI();
    }

    private void setupUI() {
        // Set up frame properties
        setTitle("Friend Management");
        setSize(1000, 800);
        setLocationRelativeTo(null);

        // Initialize the tabbedPane
        tabbedPane = new JTabbedPane();

        // Clear the tabbedPane to prevent duplicate tabs
        tabbedPane.removeAll();

        // Setup the Friend Requests Panel
        setupFriendRequestsPanel();

        // Setup the Friends Panel
        setupFriendsPanel();

        // Setup the Blocked Users Panel
        setupBlockedUsersPanel();

        // Add the tabbedPane to the frame's content pane
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    private void setupFriendRequestsPanel() {
        JPanel friendRequestsPanel = new JPanel();
        friendRequestsPanel.setLayout(new BoxLayout(friendRequestsPanel, BoxLayout.Y_AXIS));
        friendRequestsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Friend Requests"));

        // Get friend requests list (replace with your actual data)
        List<FriendRequest> friendRequests = friendService.getFriendRequests(currentUserId);

        // Loop through friend requests and add them as buttons
        for (FriendRequest friendRequest : friendRequests) {
            JButton acceptButton = new JButton("Accept Request", 12, 12);
            JButton declineButton = new JButton("Decline Request", 12, 12);

            // Set up buttons
            acceptButton.setFont(new Font("Arial", Font.PLAIN, 14));
            declineButton.setFont(new Font("Arial", Font.PLAIN, 14));
            acceptButton.setPreferredSize(new Dimension(300, 40));
            declineButton.setPreferredSize(new Dimension(300, 40));

            // Create a JPanel to hold the user photo and buttons
            JPanel requestPanel = new JPanel(new FlowLayout());

            // Profile photo
            RoundedImageLabel profilePhotoLabel = new RoundedImageLabel(userAccountService.getUserById(friendRequest.getSenderId()).getProfilePhotoPath(), 100, 100);
            profilePhotoLabel.setPreferredSize(new Dimension(100, 100));  // Set the size of the photo
            profilePhotoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // Set cursor to hand when hovering

            // Add mouse listener for clicking on the profile photo
            profilePhotoLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToProfileView(userAccountService.getUserById(friendRequest.getSenderId()));
                }
            });

            // Add the profile photo and buttons to the panel
            requestPanel.add(profilePhotoLabel);
            requestPanel.add(new JLabel(friendRequest.getSenderId()));  // Or friendRequest.getSenderUsername() if you have a username
            requestPanel.add(acceptButton);
            requestPanel.add(declineButton);
            friendRequestsPanel.add(requestPanel);

            // Add action listeners for buttons
            acceptButton.addActionListener(e -> {
                // Logic to accept the friend request
                friendService.acceptFriendRequest(currentUserId, friendRequest.getSenderId());
                // Remove the panel containing the profile photo after accepting the request
                friendRequestsPanel.remove(requestPanel);
                friendRequestsPanel.revalidate();
                friendRequestsPanel.repaint();
                JOptionPane.showMessageDialog(this, "Friend request accepted.");
            });

            declineButton.addActionListener(e -> {
                // Logic to decline the friend request
                friendService.declineFriendRequest(currentUserId, friendRequest.getSenderId());
                // Remove the panel containing the profile photo after declining the request
                friendRequestsPanel.remove(requestPanel);
                friendRequestsPanel.revalidate();
                friendRequestsPanel.repaint();
                JOptionPane.showMessageDialog(this, "Friend request declined.");
            });
        }

        // Add the friend requests panel to the tabbedPane
        tabbedPane.addTab("Friend Requests", new JScrollPane(friendRequestsPanel));
    }

    private void setupFriendsPanel() {
        JPanel friendsPanel = new JPanel();
        friendsPanel.setLayout(new BoxLayout(friendsPanel, BoxLayout.Y_AXIS));
        friendsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Friends"));

        // Get friends list (replace with your actual data)
        List<UserDTO> friends = friendService.getFriends(currentUserId);

        // Loop through the list of friends and add buttons
        for (UserDTO friend : friends) {
            JButton removeButton = new JButton("Remove Friend", 12, 12);

            // Set up the button
            removeButton.setFont(new Font("Arial", Font.PLAIN, 14));
            removeButton.setPreferredSize(new Dimension(300, 40));
            removeButton.setFocusable(false);
            // Create a panel to hold the user photo and remove button
            JPanel friendPanel = new JPanel(new FlowLayout());
            // Add action listener for removing friends
            removeButton.addActionListener(e -> {
                // Logic to remove the friend
                friendService.removeFriend(currentUserId, friend.getUserId());
                // Remove the panel containing the profile photo after removing the friend
                friendsPanel.remove(friendPanel);
                friendsPanel.revalidate();
                friendsPanel.repaint();
                JOptionPane.showMessageDialog(this, "Friend removed.");
            });

            RoundedImageLabel profilePhotoLabel = new RoundedImageLabel(friend.getProfilePhotoPath(), 100, 100);
            // Profile photo
            profilePhotoLabel.setPreferredSize(new Dimension(100, 100));  // Set the size of the photo
            profilePhotoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // Set cursor to hand when hovering

            // Add mouse listener for clicking on the profile photo
            profilePhotoLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToProfileView(friend);
                }
            });

            // Add the profile photo and remove button to the panel
            friendPanel.add(profilePhotoLabel);
            friendPanel.add(new JLabel(friend.getUsername()));  // Or other user details you want to show
            friendPanel.add(removeButton);
            friendsPanel.add(friendPanel);
        }

        // Add the friends panel to the tabbedPane
        tabbedPane.addTab("Friends", new JScrollPane(friendsPanel));
    }

    private void setupBlockedUsersPanel() {
        JPanel blockedUsersPanel = new JPanel();
        blockedUsersPanel.setLayout(new BoxLayout(blockedUsersPanel, BoxLayout.Y_AXIS));
        blockedUsersPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Blocked Users"));


        List<UserDTO> blockedUsers = friendService.getBlockedUsers(currentUserId);
        for (UserDTO blockedUser : blockedUsers) {
            JButton unblockButton = new JButton("Unblock", 12, 12);

            unblockButton.setFont(new Font("Arial", Font.PLAIN, 14));
            unblockButton.setPreferredSize(new Dimension(300, 40));
            unblockButton.setFocusable(false);
            // Create a panel to hold the user photo and unblock button
            JPanel blockedUserPanel = new JPanel(new FlowLayout());
            // Add action listener for unblocking users
            unblockButton.addActionListener(e -> {
                // Logic to unblock the user
                friendService.unblockUser(currentUserId, blockedUser.getUserId());
                // Remove the panel containing the profile photo after unblocking the user
                blockedUsersPanel.remove(blockedUserPanel);
                blockedUsersPanel.revalidate();
                blockedUsersPanel.repaint();
                JOptionPane.showMessageDialog(this, "User unblocked.");
            });

            RoundedImageLabel profilePhotoLabel = new RoundedImageLabel(blockedUser.getProfilePhotoPath(), 100, 100);
            // Profile photo
            profilePhotoLabel.setPreferredSize(new Dimension(100, 100));  // Set the size of the photo
            profilePhotoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // Set cursor to hand when hovering

            // Add the profile photo and unblock button to the panel
            blockedUserPanel.add(profilePhotoLabel);
            blockedUserPanel.add(new JLabel(blockedUser.getUsername()));  // Or other user details you want to show
            blockedUserPanel.add(unblockButton);
            blockedUsersPanel.add(blockedUserPanel);
        }

        // Add the blocked users panel to the tabbedPane
        tabbedPane.addTab("Blocked Users", new JScrollPane(blockedUsersPanel));
    }

}
