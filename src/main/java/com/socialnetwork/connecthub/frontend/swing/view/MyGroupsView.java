package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.backend.persistence.json.JsonGroupRepository;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.frontend.swing.constants.GUIConstants;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyGroupsView extends View {
    private String navigationHandlerType = "final";
    SocialNetworkAPI socialNetworkAPI;
    UserDTO user;
    JPanel panel;
    com.socialnetwork.connecthub.frontend.swing.components.JScrollPane scrollPane;

    public MyGroupsView(SocialNetworkAPI socialNetworkAPI, UserDTO userDTO) {
        this.socialNetworkAPI = socialNetworkAPI;
        this.user = userDTO;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dispose();
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToNewsFeedView(user);
            }
        });
        if(user == null){
            com.socialnetwork.connecthub.frontend.swing.components.JLabel mainLabel = new JLabel("Sorry, There is an error , please try again",25, GUIConstants.blue,5);
            mainLabel.setBounds(0, 0, getWidth(), getHeight());
            add(mainLabel);
            return;
        }

        panel = new JPanel(null);
        panel.setLayout(null);
        panel.setBounds(0, 0, 1800, 800);
        panel.setBackground(new Color(215, 215, 215));
        add(panel);


        javax.swing.JLabel myGroupsPanel = new javax.swing.JLabel("My Groups");
        myGroupsPanel.setFont(new Font("Arial", Font.BOLD, 20));
        myGroupsPanel.setForeground(new Color(56, 107, 253));
        myGroupsPanel.setBounds(60, 0, 300, 25);
        myGroupsPanel.setOpaque(false);
        panel.add(myGroupsPanel);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBackground(new Color(215, 215, 215));

        // Add friend labels to the panel
        List<GroupDTO> groups = socialNetworkAPI.getGroupService().getGroupsByUserId(user.getUserId());
        for (GroupDTO group : groups) {
            JPanel groupLabel = createGroupLabel(group);
            labelPanel.add(groupLabel);
        }

        // Adjust label panel's preferred size dynamically
        int panelHeight = Math.max(600, groups.size() * 60); // 60px per group
        labelPanel.setBounds(0, 35, 300, panelHeight);
//        labelPanel.setPreferredSize(new Dimension(300, panelHeight));

        // Add scroll pane
        scrollPane = new com.socialnetwork.connecthub.frontend.swing.components.JScrollPane(labelPanel);
        scrollPane.setBounds(0, 35, 300, 600); // Position and size
        scrollPane.setBackground(new Color(215, 215, 215));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        panel.add(scrollPane);

        RoundedImageLabel newGroupIcon = new RoundedImageLabel("src/main/java/com/socialnetwork/connecthub/resources/pics/add-group.png", 90, 90);
        newGroupIcon.setBounds(50, 650, 90, 90); // Adjust to fit within the panel
        newGroupIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open create-group view
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToGroupCreationView(user);
            }
        });
        panel.add(newGroupIcon);

        addTimeline();
        addGroupSuggestions();
        repaint();
        revalidate();
        
        
        
    }

    private JPanel createGroupLabel(GroupDTO group) {
        // Create the friend panel with a null layout for custom positioning
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        RoundedImageLabel imageLabel;
        groupPanel.setPreferredSize(new Dimension(400, 60)); // Set fixed size
        groupPanel.setMaximumSize(new Dimension(400, 60));
        groupPanel.setBackground(Color.WHITE);

        // Add rounded image for the group
        imageLabel = new RoundedImageLabel(group.getIconPhotoPath(), 50, 50);
        imageLabel.setBounds(0, 0, 40, 40); // Padding: (x, y, width, height)
        groupPanel.add(imageLabel);


        // Add group name text
        javax.swing.JLabel textLabel = new javax.swing.JLabel(group.getName());
        textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        textLabel.setFont(new Font("Arial", Font.BOLD, 13));
        textLabel.setForeground(Color.GRAY); // Ensure visible text color
        textLabel.setBounds(60, 10, 130, 30); // Adjust to fit within the panel
        textLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        groupPanel.add(textLabel);

        // Add a border for better visuals
        groupPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        textLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the group page
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToGroupView(group, user);
                dispose();
            }
        });
        imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the group page
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToGroupView(group, user);
                dispose();
            }
        });
        groupPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the group page
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToGroupView(group, user);
                dispose();
            }
        });

        return groupPanel;
    }

    private void addTimeline() {
        // Panel to hold dynamic content labels
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(215, 215, 215));

        // Add content labels to the panel
        List<ContentDTO> contentList = socialNetworkAPI.getGroupService().getUserGroupsPosts(user.getUserId());
        {
            for (ContentDTO content : contentList) {
                JPanel contentLabel = createContentLabel(content);
                contentPanel.add(contentLabel);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between posts
            }
        }
        // Adjust content panel's preferred size dynamically
        int panelHeight = 0; //Math.min(1500, contentList.size() * 800); // 1210 px per content including spacing
        for(ContentDTO content : contentList) {
            if(content.getImagePath() == null || content.getImagePath().isEmpty())
                panelHeight += 300;
            else
                panelHeight += 800;
        }
        contentPanel.setPreferredSize(new Dimension(800, panelHeight));

        // Create the scroll pane and set its bounds
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBounds(310, 0, 900, 800); // Set the size and position of the scroll pane
        scrollPane.setBackground(new Color(215, 215, 215));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrolling

        // Add the scroll pane to the main panel
        panel.add(scrollPane);

        revalidate();
        repaint();
    }

    private JPanel createContentLabel(ContentDTO content) {
        // Create the content panel with a null layout for custom positioning
        JPanel contentPanel = new JPanel(null);
        contentPanel.setBackground(Color.WHITE);
        UserDTO author = socialNetworkAPI.getUserAccountService().getUserById(content.getAuthorId());

        // Add rounded image for the author
        RoundedImageLabel authorImageLabel = new RoundedImageLabel(author.getProfilePhotoPath(), 50, 50);
        authorImageLabel.setBounds(10, 10, 50, 50); // Position the image
        contentPanel.add(authorImageLabel);
        authorImageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(author.getUserId().equals(user.getUserId()))
                    NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToMyProfileView(user);
                else
                    NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToProfileView(author, user);
                dispose();
            }
        });


        // Add author name text
        javax.swing.JLabel authorNameLabel = new javax.swing.JLabel(author.getUsername());
        authorNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        authorNameLabel.setForeground(author.isOnlineStatus()? Color.GREEN : Color.BLACK);
        authorNameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(author.getUserId().equals(user.getUserId()))
                    NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToMyProfileView(user);
                else
                    NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToProfileView(author, user);
                dispose();
            }
        });

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
        contentTextLabel.setFont(new Font("Arial", Font.PLAIN, 14));
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

    private JPanel createGroupsSuggestionLabel(GroupDTO suggestion) {
        // Create the suggestion panel with a null layout for custom positioning
        JPanel suggestionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        RoundedImageLabel imageLabel;
        suggestionPanel.setPreferredSize(new Dimension(400, 60)); // Set fixed size
        suggestionPanel.setMaximumSize(new Dimension(300, 60));
        suggestionPanel.setBackground(Color.WHITE);

        // Add rounded image for the suggestion
        imageLabel = new RoundedImageLabel(suggestion.getIconPhotoPath(), 50, 50);
        imageLabel.setBounds(0, 0, 40, 40); // Padding: (x, y, width, height)
        suggestionPanel.add(imageLabel);

        // Add username text
        javax.swing.JLabel textLabel = new javax.swing.JLabel(suggestion.getName());
        textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        textLabel.setFont(new Font("Arial", Font.BOLD, 13));
        textLabel.setForeground(Color.GRAY); // Ensure visible text color
//        textLabel.setBounds(60, 10, 130, 30); // Adjust to fit within the panel
        textLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        suggestionPanel.add(textLabel);

        boolean[] requestSent = {JsonGroupRepository.getInstance().findById(suggestion.getGroupId()).orElseThrow().getRequests().contains(user.getUserId())};

        // Add "Friend Request" button
        JButton joinButton = new JButton(requestSent[0]?" Cancel ":" join ", 5, 12);
//        joinButton.setBounds(300, 10, 150, 30); // Adjust to fit within the panel
        joinButton.setBackground(Color.BLUE);
        joinButton.setForeground(Color.WHITE);

        joinButton.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (requestSent[0]) {
                    // Cancel friend request
                    socialNetworkAPI.getGroupService().declineMember(suggestion.getGroupId(), user.getUserId()); // same implementation for cancel
                    joinButton.setText(" Join ");
                    requestSent[0] = false;
                } else {
                    // Send friend request
                    socialNetworkAPI.getGroupService().requestToJoin(suggestion.getGroupId(), user.getUserId());
                    joinButton.setText(" Cancel ");
                    requestSent[0] = true;
                }
            }
        });

        suggestionPanel.add(joinButton);

        return suggestionPanel;
    }

    private void addGroupSuggestions() {
        // Create the label
        javax.swing.JLabel groupSuggestionsLabel = new javax.swing.JLabel("Group Suggestions");
        groupSuggestionsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        groupSuggestionsLabel.setForeground(new Color(0, 0, 0, 255));
        groupSuggestionsLabel.setBounds(1240, 0, 300, 25); // Ensure these bounds fit inside the panel

        // Add the label to the main panel
        panel.add(groupSuggestionsLabel);

        // Panel to hold dynamic friend suggestion labels
        JPanel suggestionPanel = new JPanel();
        suggestionPanel.setLayout(new BoxLayout(suggestionPanel, BoxLayout.Y_AXIS));
        suggestionPanel.setBackground(new Color(215, 215, 215));

        // Add friend suggestion labels to the panel
        List<GroupDTO> groupsSuggestions = socialNetworkAPI.getGroupService().getGroupsSuggestions(user.getUserId());
        for (GroupDTO suggestion : groupsSuggestions) {
            JPanel suggestionLabel = createGroupsSuggestionLabel(suggestion);
            suggestionPanel.add(suggestionLabel);
        }

        // Adjust suggestion panel's preferred size dynamically
        int panelHeight = Math.max(600, groupsSuggestions.size() * 60); // 60px per suggestion
        suggestionPanel.setPreferredSize(new Dimension(300, panelHeight));

        // Create the scroll pane and set its orientation
        JScrollPane scrollPane = new JScrollPane(suggestionPanel);
        scrollPane.setPreferredSize(new Dimension(300, 600)); // Set the size of the scroll pane
        scrollPane.setBackground(new Color(215, 215, 215));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrolling

        // Set the scroll bar to appear on the left side
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT); // Move the scroll bar to the left

        // Set the bounds of the scroll pane
        scrollPane.setBounds(1200, 35, 300, 600); // Position and size the scroll pane

        // Add the scroll pane to the main panel
        panel.add(scrollPane);

        revalidate();
        repaint();
    }

}
