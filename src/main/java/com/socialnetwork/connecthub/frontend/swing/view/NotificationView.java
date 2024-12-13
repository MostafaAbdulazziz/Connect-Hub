package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.backend.model.*;
import com.socialnetwork.connecthub.backend.persistence.json.JsonPostRepository;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandler;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class NotificationView extends JFrame {

    private List<Notification> notifications;
    private UserDTO user;
    private SocialNetworkAPI socialNetworkAPI;
    private String navigationHandlerType = "final";
    private JFrame parentFrame;

    public NotificationView(SocialNetworkAPI socialNetworkAPI, List<Notification> notifications, UserDTO user, JFrame parentFrame) {
        this.notifications = notifications;
        this.user = user;
        this.socialNetworkAPI = socialNetworkAPI;
        this.parentFrame = parentFrame;

        setTitle("Notifications");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (Notification notification : notifications) {
            mainPanel.add(createNotificationLabel(notification)); // Create each notification label
            mainPanel.add(Box.createVerticalStrut(15));
        }
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        setVisible(true);
    }

    private JPanel createNotificationLabel(Notification notification) {
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.X_AXIS)); // Align components horizontally
        notificationPanel.setBackground(new Color(245, 245, 245)); // Light gray background for a card-like feel
        notificationPanel.setPreferredSize(new Dimension(450, 100)); // Increase width here to fit larger label
        notificationPanel.setMaximumSize(new Dimension(450, 100));
        notificationPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1)); // Light border around the panel
        notificationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);


        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));
        messagePanel.setBackground(new Color(245, 245, 245));

        // Profile photo for user (if applicable)
        if (notification instanceof FriendRequestNotification) {
            String senderId = ((FriendRequestNotification) notification).getSenderId();
            UserDTO sender = socialNetworkAPI.getUserAccountService().getUserById(senderId);
            RoundedImageLabel profileImageLabel = new RoundedImageLabel(sender.getProfilePhotoPath(), 50, 50);
            profileImageLabel.setPreferredSize(new Dimension(50, 50));
            profileImageLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            profileImageLabel.addActionListener(e -> navigateToUserProfile(sender, user));  // Navigate to user profile when image is clicked
            notificationPanel.add(profileImageLabel);

            // Notification message (Friend Request)
            JLabel messageLabel = new JLabel(notification.getMessage(), 12, Color.BLACK, Font.BOLD);
            messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            messageLabel.setForeground(new Color(50, 50, 50));
            messageLabel.setPreferredSize(new Dimension(500, 30));
            messageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    navigateToUserProfile(sender, user);  // Navigate to user profile when message is clicked
                }
            });
            messagePanel.add(messageLabel);

        } else if (notification instanceof GroupNotification) {
            GroupDTO groupDTO = socialNetworkAPI.getGroupService().getGroupById( ((GroupNotification) notification).getGroupId());
            ContentDTO contentDTO = new ContentDTO(
                    JsonPostRepository.getInstance().findById( ((GroupNotification) notification).getContentId()).orElseThrow()
            );
            JLabel messageLabel = new JLabel(notification.getMessage(), 12, Color.GRAY, Font.BOLD);
            RoundedImageLabel groupImage = new RoundedImageLabel(groupDTO.getIconPhotoPath(), 50, 50);
            groupImage.setPreferredSize(new Dimension(50, 50));
            messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            groupImage.addActionListener(e -> navigateToGroupWindow(groupDTO, user));
            notificationPanel.add(groupImage);

            messageLabel.setForeground(new Color(50, 50, 50));
            messageLabel.setPreferredSize(new Dimension(500, 30)); // Increase width here for larger message label
            messageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (contentDTO != null)
                        navigateToPost(contentDTO);  // Navigate to post when message is clicked
                    else
                        navigateToGroupWindow(groupDTO, user);  // Navigate to group window when message is clicked
                }
            });
            messagePanel.add(messageLabel);

        } else if (notification instanceof NewPostNotification) {
            ContentDTO contentDTO = new ContentDTO(
                    JsonPostRepository.getInstance().findById( ((NewPostNotification) notification).getContentId()).orElseThrow()
            );
            // Notification message (Post)
            JLabel messageLabel = new JLabel(notification.getMessage(), 12, Color.GRAY, Font.BOLD);
            messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            messageLabel.setForeground(new Color(50, 50, 50)); // Darker text for better readability
            messageLabel.setPreferredSize(new Dimension(500, 30)); // Increase width here for larger message label
            messageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    navigateToPost(contentDTO);  // Navigate to post when message is clicked
                }
            });
            messagePanel.add(messageLabel);
        }

        // Add space between the profile image and the message
        notificationPanel.add(Box.createHorizontalStrut(15));
        notificationPanel.add(messagePanel);

        return notificationPanel;
    }

    // Helper methods for navigation
    private void navigateToUserProfile(UserDTO sender, UserDTO user) {
        // Code to navigate to the user's profile
        NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToProfileView(sender, user);
        this.dispose();
        parentFrame.dispose();
        // Replace with actual navigation logic
    }

    private void navigateToGroupWindow(GroupDTO group, UserDTO user) {
        // Code to navigate to the group's page/window
        NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToGroupView(group, user);
        this.dispose();
        parentFrame.dispose();
    }

    private void navigateToPost(ContentDTO contentDTO) {
        // Code to navigate to the post
        NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToPostView(contentDTO);
    }
}
