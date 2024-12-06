package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.services.ContentService;
import com.socialnetwork.connecthub.backend.interfaces.services.FriendService;
import com.socialnetwork.connecthub.backend.interfaces.services.NewsFeedService;
import com.socialnetwork.connecthub.backend.interfaces.services.UserAccountService;
import com.socialnetwork.connecthub.backend.model.Story;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.frontend.swing.constants.GUIConstants;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import test.ContentServiceTest;
import test.FriendServiceTest;
import test.NewsFeedAPI;
import test.UserAccountServiceTest;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NewsFeedView extends View {
    RoundedImageLabel myPhotoLabel;
    JPanel myPanel;
    JLabel mainLabel;
    JPanel panel;
    JScrollPane scrollPane;
    UserDTO user;
    NewsFeedService newsFeedService;
    ContentService contentService;
    UserAccountService userAccountService;
    FriendService friendService;

    public NewsFeedView(UserDTO user) {
        newsFeedService = new NewsFeedAPI();
        contentService = new ContentServiceTest();
        userAccountService = new UserAccountServiceTest();
        friendService = new FriendServiceTest();
        this.user = user;
        setLayout(null);

        if (user == null) {
            mainLabel = new JLabel("You are not logged in!", 40, GUIConstants.blue, 5);
            mainLabel.setFont(new Font("Arial", Font.BOLD, 40));
            mainLabel.setForeground(GUIConstants.blue);
            mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainLabel.setVerticalAlignment(SwingConstants.CENTER);
            mainLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
            add(mainLabel);
            revalidate();
            repaint();
            return;
        }

        panel = new JPanel(null){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon("src/com/socialnetwork/connecthub/resources/pics/BG1.jpg").getImage(), 0, 0, null)
                ;
            }
        };
        panel.setLayout(null);
        panel.setBounds(0, 0, 1800, 800);
        panel.setBackground(new Color(215, 215, 215));
        add(panel);

        myPanel = new JPanel(null);
        myPanel.setBounds(0, 0, 300, 110);
        myPanel.setBackground(new Color(255, 255, 255));
        myPanel.setVisible(true);
        myPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new MyProfileView(user);
                dispose();
            }
        });
        panel.add(myPanel);
        if (user.getProfilePhotoPath() == null || user.getProfilePhotoPath().isEmpty()) {
            myPhotoLabel = new RoundedImageLabel("src/com/socialnetwork/connecthub/resources/pics/friends.png", 100, 100);
        } else {
            myPhotoLabel = new RoundedImageLabel("src/test/Screenshot 2024-12-03 011157.png", 100, 100);
        }
        myPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 6));
        myPhotoLabel.setBounds(8, 8, 100, 100);
        myPhotoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new MyProfileView(user);
                dispose();
            }
        });
        myPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new MyProfileView(user);
                dispose();
            }
        });
        myPanel.setOpaque(true);
        javax.swing.JLabel myNameLabel = new javax.swing.JLabel("userName");
        myNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        myNameLabel.setForeground(GUIConstants.blue);
        myNameLabel.setBounds(140, 28, 200, 50);
        myNameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new MyProfileView(user);
                dispose();
            }
        });
        myPanel.add(myNameLabel);

        javax.swing.JLabel OnlineFriendsLabel = new javax.swing.JLabel("Online Friends");
        OnlineFriendsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        OnlineFriendsLabel.setForeground(new Color(27, 140, 0));
        OnlineFriendsLabel.setBounds(60, 120, 300, 20);
        OnlineFriendsLabel.setOpaque(false);
        panel.add(OnlineFriendsLabel);


        myPanel.add(myPhotoLabel);


        // Panel to hold dynamic friend labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBackground(new Color(215, 215, 215));

        // Add friend labels to the panel
        List<UserDTO> friends = newsFeedService.getOnlineFriends(user.getUserId());
        for (UserDTO friend : friends) {
            JPanel friendLabel = createFriendLabel(friend);
            labelPanel.add(friendLabel);
        }

        // Adjust label panel's preferred size dynamically
        int panelHeight = Math.max(1000, friends.size() * 100); // 60px per friend
        labelPanel.setPreferredSize(new Dimension(300, panelHeight));

        // Add scroll pane
        scrollPane = new com.socialnetwork.connecthub.frontend.swing.components.JScrollPane(labelPanel);
        scrollPane.setBounds(0, 150, 300, 600); // Position and size
        scrollPane.setBackground(new Color(215, 215, 215));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        panel.add(scrollPane);


        addTimeline();
        addStories();
        addButtons();
        revalidate();
        addFriendSuggestions();
        repaint();
    }











    private void addTimeline() {
        // Panel to hold dynamic content labels
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(215, 215, 215));

        // Add content labels to the panel
        List<ContentDTO> contentList = newsFeedService.getNewsFeed(user.getUserId());
        for (ContentDTO content : contentList) {
            JPanel contentLabel = createContentLabel(content);
            contentPanel.add(contentLabel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between posts
        }

        // Adjust content panel's preferred size dynamically
        int panelHeight = Math.max(1500, contentList.size() * 1210); // 1210px per content including spacing
        contentPanel.setPreferredSize(new Dimension(800, panelHeight));

        // Create the scroll pane and set its bounds
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBounds(310, 150, 900, 600); // Set the size and position of the scroll pane
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

        // Add rounded image for the author
        if (content.getImagePath() == null || content.getImagePath().isEmpty()) {
            RoundedImageLabel authorImageLabel = new RoundedImageLabel("src/com/socialnetwork/connecthub/resources/pics/friends.png", 50, 50);
            authorImageLabel.setBounds(10, 10, 50, 50); // Position the image
            contentPanel.add(authorImageLabel);
            authorImageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    // Open the author's profile
                    new ProfileView(userAccountService.getUserById(content.getAuthorId()));
                    dispose();
                }
            });
        }
        else {
            RoundedImageLabel authorImageLabel = new RoundedImageLabel(content.getImagePath(), 50, 50);
            authorImageLabel.setBounds(10, 10, 50, 50); // Position the image
            contentPanel.add(authorImageLabel);
            authorImageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    // Open the author's profile
                    new ProfileView(userAccountService.getUserById(content.getAuthorId()));
                    dispose();
                }
            });
        }


        // Add author name text
        javax.swing.JLabel authorNameLabel = new javax.swing.JLabel(userAccountService.getUserById(content.getAuthorId()).getUsername());
        authorNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        authorNameLabel.setForeground(Color.BLACK);
        for(UserDTO user1 : newsFeedService.getOnlineFriends(user.getUserId())){
            if(user1.getUserId().equals(content.getAuthorId())){
                authorNameLabel.setForeground(Color.GREEN);
                break;
            }
        }
        authorNameLabel.setBounds(70, 20, 200, 30); // Adjusted position
        authorNameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the author's profile
                new ProfileView(userAccountService.getUserById(content.getAuthorId()));
                dispose();
            }
        });
        contentPanel.add(authorNameLabel);

        // Add timestamp text
        javax.swing.JLabel timestampLabel = new javax.swing.JLabel(content.getTimestamp().toString());
        timestampLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timestampLabel.setForeground(Color.GRAY);
        timestampLabel.setBounds(650, 20, 170, 30); // Adjusted position
        contentPanel.add(timestampLabel);

        // Add content text
        javax.swing.JLabel contentTextLabel = new javax.swing.JLabel("<html>" + content.getContent() + "</html>");
        contentTextLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        contentTextLabel.setForeground(Color.BLACK);
        contentTextLabel.setBounds(50, 80, 750, content.getContent().length() / 5); // Adjusted position and size
//        contentTextLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        contentPanel.add(contentTextLabel);

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

            contentImagePanel.setBounds(70, contentTextLabel.getHeight() + 100, 700, 400); // Position the image panel
            contentPanel.add(contentImagePanel);
        }

        // Add spacing at the bottom to maintain consistent height


        // Add a border for better visuals
        contentPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 5));
//        contentPanel.setPreferredSize(new Dimension(800, contentPanel.getY()+400)); // Set fixed size
        if( contentImagePanel != null){
        contentPanel.setMaximumSize(new Dimension(1900, contentImagePanel.getY() + 450));
        }
        else
          {
            contentPanel.setMaximumSize(new Dimension(1900, 450));
         }

        return contentPanel;
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

    private void addStories() {
        // Add horizontal scroll panel for rounded labels
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align labels from left
        horizontalPanel.setBackground(new Color(215, 215, 215)); // Set background color

        // Add friends' stories to the panel
        for (ContentDTO story : contentService.getFriendsStories("9999999999")) {
            JPanel storyPanel = createStoryPanel(story); // Create a panel for each story
            horizontalPanel.add(storyPanel);
        }

        // Add user's own stories to the panel
        for (ContentDTO story : contentService.getUserStories(user.getUserId())) {
            JPanel storyPanel = createStoryPanel(story); // Create a panel for each story
            horizontalPanel.add(storyPanel);
        }

        // Set preferred size to ensure scroll works properly
        int totalStories = contentService.getFriendsStories(user.getUserId()).size() + contentService.getUserStories(user.getUserId()).size();
        int panelWidth = Math.max(1000, totalStories * 110); // 110px per label including spacing
        horizontalPanel.setPreferredSize(new Dimension(panelWidth, 120));

        // Create the horizontal scroll pane
        com.socialnetwork.connecthub.frontend.swing.components.JScrollPane horizontalScrollPane = new com.socialnetwork.connecthub.frontend.swing.components.JScrollPane(horizontalPanel);
        horizontalScrollPane.setBounds(310, 0, 1000, 120); // Set position and size
        horizontalScrollPane.setBackground(new Color(215, 215, 215));
        horizontalScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));

        // Ensure horizontal scrolling
        horizontalScrollPane.setHorizontalScrollBarPolicy(com.socialnetwork.connecthub.frontend.swing.components.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        horizontalScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // Disable vertical scrolling

        // Add the horizontal scroll pane to the main panel
        panel.add(horizontalScrollPane);

        // Repaint and revalidate to ensure proper rendering
        revalidate();
        repaint();
    }

    private JPanel createStoryPanel(ContentDTO story) {
        // Create a panel for each story
        JPanel storyPanel = new JPanel();
        storyPanel.setLayout(new BoxLayout(storyPanel, BoxLayout.Y_AXIS)); // Vertical layout
        storyPanel.setPreferredSize(new Dimension(110, 120)); // Set size for each story panel
        storyPanel.setBackground(new Color(215, 215, 215)); // Set background color

        // Add the rounded image label for the story
        String imagePath = (story.getImagePath() == null || story.getImagePath().isEmpty()) ?
                "src/com/socialnetwork/connecthub/resources/pics/friends.png" :
                story.getImagePath();

        RoundedImageLabel storyImageLabel = new RoundedImageLabel(imagePath, 75, 75);
        storyImageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor for interactivity
        storyPanel.add(storyImageLabel);

        // Add the username label below the image
        javax.swing.JLabel usernameLabel = new javax.swing.JLabel("9999"); // Assuming story has the user's name
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.black); // Light color for the name
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center-align the name
        usernameLabel.setOpaque(false); // Transparent label
        storyPanel.add(usernameLabel, BorderLayout.CENTER);

        // Add mouse listener for opening the user's profile
        storyImageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the user's profile on click
                new StoryView(story);
            }
        });

        return storyPanel;
    }

    private void addFriendSuggestions() {
    // Create the label
    javax.swing.JLabel friendSuggestionsLabel = new javax.swing.JLabel("Friend Suggestions");
    friendSuggestionsLabel.setFont(new Font("Arial", Font.BOLD, 20));
    friendSuggestionsLabel.setForeground(new Color(0, 0, 0, 255));
    friendSuggestionsLabel.setBounds(1240, 120, 300, 20); // Ensure these bounds fit inside the panel

    // Add the label to the main panel
    panel.add(friendSuggestionsLabel);

    // Panel to hold dynamic friend suggestion labels
    JPanel suggestionPanel = new JPanel();
    suggestionPanel.setLayout(new BoxLayout(suggestionPanel, BoxLayout.Y_AXIS));
    suggestionPanel.setBackground(new Color(215, 215, 215));

    // Add friend suggestion labels to the panel
    List<UserDTO> friendSuggestions = newsFeedService.getFriendSuggestions(user.getUserId());
    for (UserDTO suggestion : friendSuggestions) {
        JPanel suggestionLabel = createFriendSuggestionLabel(suggestion);
        suggestionPanel.add(suggestionLabel);
    }

    // Adjust suggestion panel's preferred size dynamically
    int panelHeight = Math.max(600, friendSuggestions.size() * 60); // 60px per suggestion
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
    scrollPane.setBounds(1200, 150, 300, 600); // Position and size the scroll pane

    // Add the scroll pane to the main panel
    panel.add(scrollPane);

    revalidate();
    repaint();
}



    private void addButtons()
    {
        com.socialnetwork.connecthub.frontend.swing.components.JButton createButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Create Post", 5, 12);
        createButton.setBounds(310, 120, 150, 30); // Adjust to fit within the panel
        createButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the create post view
                new ContentCreationAreaView(contentService, user);
                dispose();
            }
        });
        panel.add(createButton);

        com.socialnetwork.connecthub.frontend.swing.components.JButton refreshButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Refresh", 5, 12);
        refreshButton.setBounds(670, 120, 150, 30); // Adjust to fit within the panel
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Refresh the news feed
                new NewsFeedView(user);
                dispose();
            }
        });
        panel.add(refreshButton);

        com.socialnetwork.connecthub.frontend.swing.components.JButton logoutButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Logout", 5, 12);
        logoutButton.setBounds(1030, 120, 150, 30); // Adjust to fit within the panel
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Logout the user
                new LoginView();
                dispose();
            }
        });
        panel.add(logoutButton);


        com.socialnetwork.connecthub.frontend.swing.components.JButton createStoryButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Create Story", 5, 12);
        createStoryButton.setBounds(1330, 80, 150, 30); // Adjust to fit within the panel
        createStoryButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the create story view

                new StoryCreationAreaView(contentService, user);

            }
        });
        panel.add(createStoryButton);

    }




private JPanel createFriendSuggestionLabel(UserDTO suggestion) {
    // Create the suggestion panel with a null layout for custom positioning
    JPanel suggestionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    RoundedImageLabel imageLabel;
    suggestionPanel.setPreferredSize(new Dimension(400, 60)); // Set fixed size
    suggestionPanel.setMaximumSize(new Dimension(300, 60));
    suggestionPanel.setBackground(Color.WHITE);

    // Add rounded image for the suggestion
    if (suggestion.getProfilePhotoPath() == null || suggestion.getProfilePhotoPath().isEmpty()) {
        imageLabel = new RoundedImageLabel("src/com/socialnetwork/connecthub/resources/pics/friends.png", 50, 50);
        imageLabel.setBounds(0, 0, 40, 40); // Padding: (x, y, width, height)
        suggestionPanel.add(imageLabel);
    } else {
        imageLabel = new RoundedImageLabel(suggestion.getProfilePhotoPath(), 50, 50);
        imageLabel.setBounds(0, 0, 40, 40); // Padding: (x, y, width, height)
        suggestionPanel.add(imageLabel);
    }

    // Add username text
    javax.swing.JLabel textLabel = new javax.swing.JLabel(suggestion.getUsername());
    textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    textLabel.setFont(new Font("Arial", Font.BOLD, 13));
    textLabel.setForeground(Color.GRAY); // Ensure visible text color
    textLabel.setBounds(60, 10, 130, 30); // Adjust to fit within the panel
    textLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    suggestionPanel.add(textLabel);

    // Add "Friend Request" button
    com.socialnetwork.connecthub.frontend.swing.components.JButton friendRequestButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton(" Send Friend Request ", 5, 12);
    friendRequestButton.setBounds(300, 10, 150, 30); // Adjust to fit within the panel
    friendRequestButton.setBackground(Color.BLUE);
    friendRequestButton.setForeground(Color.WHITE);

    friendRequestButton.addMouseListener(new java.awt.event.MouseAdapter() {
        private boolean requestSent = false;

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (requestSent) {
                // Cancel friend request
                friendService.declineFriendRequest(user.getUserId(), suggestion.getUserId());
                friendRequestButton.setText(" Sent Friend Request ");
                friendRequestButton.setBackground(Color.BLUE);
                requestSent = false;
                System.out.println("Friend request canceled for " + suggestion.getUsername());
            } else {
                // Send friend request
                friendService.sendFriendRequest(user.getUserId(), suggestion.getUserId());
                friendRequestButton.setText(" Cancel ");
                requestSent = true;
                System.out.println("Friend request sent to " + suggestion.getUsername());
            }
        }
    });

    suggestionPanel.add(friendRequestButton);

    // Add a border for better visuals
    suggestionPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
    textLabel.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            // Open the suggestion's profile
            new ProfileView(suggestion);
            dispose();
        }
    });
    imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            // Open the suggestion's profile
            new ProfileView(suggestion);
            dispose();
        }
    });
    suggestionPanel.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            // Open the suggestion's profile
            new ProfileView(suggestion);
            dispose();
        }
    });

    return suggestionPanel;
}





}
