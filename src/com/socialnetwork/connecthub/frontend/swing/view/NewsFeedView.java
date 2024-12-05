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

        panel = new JPanel(null);
        panel.setLayout(null);
        panel.setBounds(0, 0, 1800, 800);
        panel.setBackground(new Color(215, 215, 215));
        add(panel);

        myPanel = new JPanel(null);
        myPanel.setBounds(0, 0, 300, 110);
        myPanel.setBackground(new Color(255, 255, 255));
        myPanel.setVisible(true);
        panel.add(myPanel);
        if (user.getProfilePhotoPath() == null || user.getProfilePhotoPath().isEmpty()) {
            myPhotoLabel = new RoundedImageLabel("src/com/socialnetwork/connecthub/resources/pics/friends.png", 100, 100);
        } else {
            myPhotoLabel = new RoundedImageLabel("src/test/Screenshot 2024-12-03 011157.png", 100, 100);
        }
        myPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 6));
        myPhotoLabel.setBounds(8, 8, 100, 100);
        myPanel.setOpaque(true);
        javax.swing.JLabel myNameLabel = new javax.swing.JLabel("userName");
        myNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        myNameLabel.setForeground(Color.GRAY);
        myNameLabel.setBounds(120, 8, 200, 50);
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
        int panelHeight = Math.max(600, friends.size() * 60); // 60px per friend
        labelPanel.setPreferredSize(new Dimension(300, panelHeight));

        // Add scroll pane
        scrollPane = new com.socialnetwork.connecthub.frontend.swing.components.JScrollPane(labelPanel);
        scrollPane.setBounds(0, 150, 300, 600); // Position and size
        scrollPane.setBackground(new Color(215, 215, 215));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        panel.add(scrollPane);


        addTimeline();
        addStories();
        addFriendSuggestions();
        revalidate();
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
    }

    // Adjust content panel's preferred size dynamically
    int panelHeight = Math.max(600, contentList.size() * 400); // 400px per content
    contentPanel.setPreferredSize(new Dimension(580, panelHeight));

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
    contentPanel.setPreferredSize(new Dimension(700, 400)); // Set fixed size
    contentPanel.setBackground(Color.WHITE);

    // Add rounded image for the author
    RoundedImageLabel authorImageLabel = new RoundedImageLabel(content.getImagePath(), 50, 50);
    authorImageLabel.setBounds(10, 10, 50, 50); // Position the image
    contentPanel.add(authorImageLabel);

    // Add author name text
    javax.swing.JLabel authorNameLabel = new javax.swing.JLabel(userAccountService.getUserById(content.getAuthorId()).getUsername());
    authorNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
    authorNameLabel.setForeground(Color.BLACK);
    authorNameLabel.setBounds(70, 26, 200, 20); // Position the name
    contentPanel.add(authorNameLabel);

    // Add timestamp text
    javax.swing.JLabel timestampLabel = new javax.swing.JLabel(content.getTimestamp().toString());
    timestampLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    timestampLabel.setForeground(Color.GRAY);
    timestampLabel.setBounds(700, 10, 170, 20); // Position the timestamp
    contentPanel.add(timestampLabel);

    // Add content text
    javax.swing.JLabel contentTextLabel = new javax.swing.JLabel("<html>" + content.getContent() + "</html>");
    contentTextLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    contentTextLabel.setForeground(Color.BLACK);
    contentTextLabel.setBounds(10, 70, 650, 70); // Position the content text
    contentPanel.add(contentTextLabel);

    // Add content image if available
    if (content.getImagePath() != null && !content.getImagePath().isEmpty()) {
        javax.swing.JLabel contentImageLabel = new javax.swing.JLabel(new ImageIcon(content.getImagePath()));
        contentImageLabel.setBounds(70, 110, 500, 250); // Position the content image
        contentPanel.add(contentImageLabel);
    }

    // Add a border for better visuals
    contentPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

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
                new StoryView(userAccountService.getUserById(story.getAuthorId()));
                dispose();
            }
        });

        return storyPanel;
    }

    private void addFriendSuggestions() {
    // Label for friend suggestions
    javax.swing.JLabel friendSuggestionsLabel = new javax.swing.JLabel("Friend Suggestions");
    friendSuggestionsLabel.setFont(new Font("Arial", Font.BOLD, 20));
    friendSuggestionsLabel.setForeground(new Color(0, 140, 124));
    friendSuggestionsLabel.setBounds(0, 0, 300, 20); // Position the label to the left of the scroll pane

    // Panel to hold the label and scroll pane side by side
    JPanel labelAndScrollPanel = new JPanel();
    labelAndScrollPanel.setLayout(new BorderLayout()); // Use BorderLayout for better control
    labelAndScrollPanel.setBackground(new Color(215, 215, 215));

    // Panel to hold dynamic friend suggestion labels
    JPanel suggestionPanel = new JPanel();
    suggestionPanel.setLayout(new BoxLayout(suggestionPanel, BoxLayout.Y_AXIS));
    suggestionPanel.setBackground(new Color(215, 215, 215));

    // Add friend suggestion labels to the panel
    List<UserDTO> friendSuggestions = friendService.getFriendSuggestions(user.getUserId());
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

    // Add the scroll pane and label to the panel
    labelAndScrollPanel.add(scrollPane, BorderLayout.WEST); // Scroll pane on the left
    labelAndScrollPanel.add(friendSuggestionsLabel, BorderLayout.EAST); // Label on the right

    // Set the bounds of the label and scroll panel
    labelAndScrollPanel.setBounds(1200, 150, 600, 600); // Position and size the container

    panel.add(labelAndScrollPanel);

    revalidate();
    repaint();
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
    com.socialnetwork.connecthub.frontend.swing.components.JButton friendRequestButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton(" Sent Friend Request ", 5, 12);
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


//    private addRequiredButtons(){
//
//    }


}
