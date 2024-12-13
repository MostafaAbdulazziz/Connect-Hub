package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.backend.model.Group;
import com.socialnetwork.connecthub.backend.model.GroupNotification;
import com.socialnetwork.connecthub.backend.service.java.JavaGroupService;
import com.socialnetwork.connecthub.backend.service.java.JavaUserAccountService;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.frontend.swing.constants.GUIConstants;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import com.socialnetwork.connecthub.shared.exceptions.ContentCreationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class GroupView extends View {
    SocialNetworkAPI socialNetworkAPI;
    GroupDTO group;
    UserDTO user;
    JPanel panel;
//    JPanel backgroundPanel;
    com.socialnetwork.connecthub.frontend.swing.components.JScrollPane scrollPane;
    private final String navigationHandlerType = "final";

    public GroupView(SocialNetworkAPI socialNetworkAPI, GroupDTO groupDTO, UserDTO userDTO) {
        this.socialNetworkAPI = socialNetworkAPI;
        this.group = groupDTO;
        this.user = userDTO;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevent default behavior

        // Add a custom window listener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToNewsFeedView(userDTO);
            }
        });
        panel = new JPanel(null);
        panel.setLayout(null);
        panel.setBounds(0, 0, getWidth(), getHeight());
        panel.setBackground(new Color(215, 215, 215));
        add(panel);





        javax.swing.JLabel membersPanel = new javax.swing.JLabel("Members");
        membersPanel.setFont(new Font("Arial", Font.BOLD, 20));
        membersPanel.setForeground(new Color(27, 140, 0));
        membersPanel.setBounds(0, 0, 300, 25);
        membersPanel.setOpaque(false);
        panel.add(membersPanel);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBackground(new Color(215, 215, 215));

        // Add friend labels to the panel
        List<UserDTO> members = socialNetworkAPI.getGroupService().getGroupMembers(groupDTO.getGroupId());
        for (UserDTO member : members) {
            JPanel memberLabel = createGroupMemberLabel(member);
            labelPanel.add(memberLabel);
        }

        // Adjust label panel's preferred size dynamically
        int panelHeight = Math.max(1000, members.size() * 100); // 60px per friend
        labelPanel.setPreferredSize(new Dimension(300, panelHeight));

        // Add scroll pane
        scrollPane = new com.socialnetwork.connecthub.frontend.swing.components.JScrollPane(labelPanel);
        scrollPane.setBounds(0, 35, 300, 600); // Position and size
        scrollPane.setBackground(new Color(215, 215, 215));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        panel.add(scrollPane);
        JLabel postLabel = new JLabel(group.getName(), JLabel.CENTER, GUIConstants.blue, 20);
        postLabel.setBounds(310, 0, 900, 50);
        panel.add(postLabel);

        addTimeline();
        addButtons();
        repaint();
        revalidate();


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
        if(socialNetworkAPI.getGroupService().getMembershipType(group.getGroupId(), user.getUserId()) == JavaGroupService.MembershipType.ADMIN || socialNetworkAPI.getGroupService().getMembershipType(group.getGroupId(), user.getUserId()) == JavaGroupService.MembershipType.PRIMARY_ADMIN){
            com.socialnetwork.connecthub.frontend.swing.components.JButton editButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("edit", 5, 12);
            editButton.setBounds(400,20,100,30);
            editButton.setBackground(Color.BLUE);
            editButton.setForeground(Color.WHITE);
            editButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToContentCreationAreaView(user, group);
                    dispose();
                }
            });
            contentPanel.add(editButton);
            com.socialnetwork.connecthub.frontend.swing.components.JButton deleteButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("delete", 5, 12);
            deleteButton.setBounds(510,20,100,30);
            deleteButton.setBackground(Color.red);
            deleteButton.setForeground(Color.WHITE);
            deleteButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    socialNetworkAPI.getGroupService().deletePost(group.getGroupId(), user.getUserId(),content);
                    deleteButton.setText("Deleted");
                    deleteButton.setEnabled(false);
                }
            });

            contentPanel.add(deleteButton);


        }

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

    private void addTimeline() {
        // Panel to hold dynamic content labels
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(215, 215, 215));

        // Add content labels to the panel
        List<ContentDTO> contentList = socialNetworkAPI.getGroupService().getGroupPosts(group.getGroupId());
        for (ContentDTO content : contentList) {
            JPanel contentLabel = createContentLabel(content);
            contentPanel.add(contentLabel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between posts
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
        scrollPane.setBounds(310, 0, 900, 600); // Set the size and position of the scroll pane
        scrollPane.setBackground(new Color(215, 215, 215));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrolling

        // Add the scroll pane to the main panel
        panel.add(scrollPane);



        revalidate();
        repaint();
    }

    private JPanel createGroupMemberLabel(UserDTO member) {
        // Create the friend panel with a null layout for custom positioning
        JPanel memberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        RoundedImageLabel imageLabel;
        memberPanel.setPreferredSize(new Dimension(400, 60)); // Set fixed size
        memberPanel.setMaximumSize(new Dimension(400, 60));
        memberPanel.setBackground(Color.WHITE);

        // Add rounded image for the friend
        imageLabel = new RoundedImageLabel(member.getProfilePhotoPath(), 50, 50);
        imageLabel.setBounds(0, 0, 40, 40); // Padding: (x, y, width, height)
        memberPanel.add(imageLabel);


        // Add username text
        javax.swing.JLabel textLabel = new javax.swing.JLabel(member.getUsername());
        textLabel.setFont(new Font("Arial", Font.BOLD, 13));
        textLabel.setForeground(Color.GRAY); // Ensure visible text color
        textLabel.setBounds(60, 10, 130, 30); // Adjust to fit within the panel
        textLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        memberPanel.add(textLabel);

        // Add a border for better visuals
        memberPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        textLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the user's profile
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToProfileView(member, user);
                dispose();
            }
        });
        imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the user's profile
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToProfileView(member, user);
                dispose();
            }
        });
        memberPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open the user's profile
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToProfileView(member, user);
                dispose();
            }
        });

        if(
                ( socialNetworkAPI.getGroupService().getMembershipType(group.getGroupId(), user.getUserId()) == JavaGroupService.MembershipType.ADMIN
                || socialNetworkAPI.getGroupService().getMembershipType(group.getGroupId(), user.getUserId()) == JavaGroupService.MembershipType.PRIMARY_ADMIN )
                && socialNetworkAPI.getGroupService().getMembershipType(group.getGroupId(), member.getUserId()) != JavaGroupService.MembershipType.PRIMARY_ADMIN
                && member.getUserId() != user.getUserId()
        ) {
            JButton removeMemberButton = new JButton(" remove ", 5, 12);
            removeMemberButton.setBounds(300, 10, 150, 30); // Adjust to fit within the panel
            removeMemberButton.setBackground(Color.RED);
            removeMemberButton.setForeground(Color.WHITE);
            removeMemberButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            removeMemberButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // Remove the friend
                    socialNetworkAPI.getGroupService().removeMember(group.getGroupId(), member.getUserId());
                    removeMemberButton.setText("Removed");
                    removeMemberButton.setEnabled(false);
                }
            });
            memberPanel.add(removeMemberButton);
        }

        

        return memberPanel;
    }

    public void addButtons(){
        int previousY = 0;
        com.socialnetwork.connecthub.frontend.swing.components.JButton createPostButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Create Post", 5, 12);
        createPostButton.setBounds(1250,0, 200, 50);
        createPostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToContentCreationAreaView(user, group);

            }
        });
        panel.add(createPostButton);
        previousY += 60;
        if(socialNetworkAPI.getGroupService().getMembershipType(group.getGroupId(), user.getUserId()) == JavaGroupService.MembershipType.ADMIN || socialNetworkAPI.getGroupService().getMembershipType(group.getGroupId(), user.getUserId()) == JavaGroupService.MembershipType.PRIMARY_ADMIN) {


            com.socialnetwork.connecthub.frontend.swing.components.JButton manageJoinRequestsButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Manage Join Requests", 5, 12);
            manageJoinRequestsButton.setBounds(1250, previousY, 200, 50);
            manageJoinRequestsButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToJoinRequestsView(group,user);
                }


            });

            panel.add(manageJoinRequestsButton);
            previousY += 60;
        }

        if(socialNetworkAPI.getGroupService().getMembershipType(group.getGroupId(), user.getUserId()) == JavaGroupService.MembershipType.PRIMARY_ADMIN)
        {
            com.socialnetwork.connecthub.frontend.swing.components.JButton deleteGroupButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Delete Group", 5, 12);
            deleteGroupButton.setBounds(1250, previousY, 200, 50);
            deleteGroupButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    socialNetworkAPI.getGroupService().deleteGroup(group.getGroupId());
                    deleteGroupButton.setText("Deleted");
                    deleteGroupButton.setEnabled(false);
                }
            });
            previousY += 60;

            com.socialnetwork.connecthub.frontend.swing.components.JButton manageAdminsButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Manage Admins", 5, 12);
            manageAdminsButton.setBounds(1250, previousY, 200, 50);
            manageAdminsButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToManageAdminsView(group,user);
                }
            });
            previousY += 60;
            panel.add(manageAdminsButton);
            panel.add(deleteGroupButton);

        }

    if(socialNetworkAPI.getGroupService().getMembershipType(group.getGroupId(), user.getUserId()) == JavaGroupService.MembershipType.MEMBER)
    {
        com.socialnetwork.connecthub.frontend.swing.components.JButton leaveGroupButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Leave Group", 5, 12);
        leaveGroupButton.setBounds(1250, previousY, 200, 50);
        leaveGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                socialNetworkAPI.getGroupService().removeMember(group.getGroupId(), user.getUserId());
                leaveGroupButton.setText("Left");
                leaveGroupButton.setEnabled(false);
            }
        });
        panel.add(leaveGroupButton);
    }
    }


}
