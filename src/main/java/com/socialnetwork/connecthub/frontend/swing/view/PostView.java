package com.socialnetwork.connecthub.frontend.swing.view;

import javax.swing.*;
import java.awt.*;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;

public class PostView extends JFrame {
    private final SocialNetworkAPI socialNetworkAPI;
    private final ContentDTO content;

    public PostView(SocialNetworkAPI socialNetworkAPI, ContentDTO content) {
        this.content = content;
        this.socialNetworkAPI = socialNetworkAPI;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Post View");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contentPanel = createContentLabel(content);
        contentPanel.setBounds(10, 10, 760, contentPanel.getHeight());
        add(contentPanel);

        JButton closeButton = new JButton("Close", 10, 14);
        closeButton.setBounds(350, contentPanel.getHeight() + 20, 100, 30);
        closeButton.addActionListener(e -> dispose());
        add(closeButton);

        setVisible(true);
    }

    private JPanel createContentLabel(ContentDTO content) {
        // Create the content panel
        JPanel contentPanel = new JPanel(null);
        contentPanel.setBackground(Color.WHITE);

        // Add rounded image for the author
        RoundedImageLabel authorImageLabel = new RoundedImageLabel(socialNetworkAPI.getUserAccountService().getUserById(content.getAuthorId()).getProfilePhotoPath(), 50, 50);
        authorImageLabel.setBounds(10, 10, 50, 50);
        contentPanel.add(authorImageLabel);

        // Add author name text
        JLabel authorNameLabel = new JLabel(socialNetworkAPI.getUserAccountService().getUserById(content.getAuthorId()).getUsername());
        authorNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        authorNameLabel.setForeground(Color.BLACK);
        authorNameLabel.setBounds(70, 20, 200, 30);
        contentPanel.add(authorNameLabel);

        // Add timestamp text
        JLabel timestampLabel = new JLabel(content.getTimestamp().toString());
        timestampLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timestampLabel.setForeground(Color.GRAY);
        timestampLabel.setBounds(530, 20, 200, 30);
        contentPanel.add(timestampLabel);

        // Add content text
        JLabel contentTextLabel = new JLabel("<html>" + content.getContent().replace("\n", "<br>") + "</html>");
        contentTextLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        contentTextLabel.setForeground(Color.BLACK);

        // Calculate dynamic height based on text length
        int textHeight = (content.getContent().split("\n").length + 1) * 20;
        contentTextLabel.setBounds(50, 80, 700, textHeight);
        contentPanel.add(contentTextLabel);

        // Add content image if image path is valid
        JPanel contentImagePanel = null;
        if (content.getImagePath() != null && !content.getImagePath().isEmpty()) {
            contentImagePanel = new JPanel() {
                private final Image image = new ImageIcon(content.getImagePath()).getImage();

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            };
            contentImagePanel.setBounds(50, textHeight + 100, 700, 400);
            contentPanel.add(contentImagePanel);
        }

        // Add a border for better visuals
        contentPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 5));

        // Set the size of contentPanel dynamically
        if (contentImagePanel != null) {
            contentPanel.setSize(760, contentImagePanel.getY() + 450);
        } else {
            contentPanel.setSize(760, textHeight + 150);
        }

        contentPanel.repaint();
        contentPanel.revalidate();

        return contentPanel;
    }
}
