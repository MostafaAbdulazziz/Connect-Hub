package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;


public class StoryView extends View {
    private ContentDTO content;
    @Getter
    private JPanel panel;

    public StoryView(ContentDTO content) {
        setSize(800, 800);
        setLocationRelativeTo(null);
        this.content = content;
        initializeComponents();
        add(getPanel());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeComponents() {
        // Create the main panel
        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(800, 800);
        panel.setBackground(Color.BLACK);

        // Add an image to simulate the story's background
        JLabel imageLabel = new JLabel();
        if (content.getImagePath() != null && !content.getImagePath().isEmpty()) {
            ImageIcon imageIcon = new ImageIcon(content.getImagePath());
            Image scaledImage = imageIcon.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            imageLabel.setBackground(Color.DARK_GRAY);
            imageLabel.setOpaque(true);
        }
        imageLabel.setBounds(0, 0, 800, 800);

        // Add a label for the author's name
        JLabel authorLabel = new JLabel("Author: " + content.getAuthorId());
        authorLabel.setForeground(Color.WHITE);
        authorLabel.setFont(new Font("Arial", Font.BOLD, 18));
        authorLabel.setBounds(20, 20, 300, 30);

        // Add a timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
        JLabel timestampLabel = new JLabel("Posted: " + dateFormat.format(content.getTimestamp()));
        timestampLabel.setForeground(Color.LIGHT_GRAY);
        timestampLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        timestampLabel.setBounds(20, 60, 300, 20);

        // Add content text
        JTextArea contentText = new JTextArea(content.getContent());
        contentText.setFont(new Font("Arial", Font.PLAIN, 16));
        contentText.setForeground(Color.WHITE);
        contentText.setBackground(new Color(0, 0, 0, 0)); // Transparent background
        contentText.setLineWrap(true);
        contentText.setWrapStyleWord(true);
        contentText.setEditable(false);
        contentText.setBounds(20, 500, 760, 400);
        contentText.setOpaque(false);


        // Add components to the panel
        panel.add(imageLabel);
        panel.add(authorLabel);
        panel.add(timestampLabel);
        panel.add(contentText);


        // Bring text components to the front
        imageLabel.setOpaque(false);
        imageLabel.setLayout(null);
        panel.setComponentZOrder(imageLabel, panel.getComponentCount() - 1);
    }

}