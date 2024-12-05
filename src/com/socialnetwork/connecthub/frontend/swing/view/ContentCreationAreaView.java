package com.socialnetwork.connecthub.frontend.swing.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;
import com.socialnetwork.connecthub.frontend.swing.constants.GUIConstants;

public class ContentCreationAreaView extends JFrame {

    private JTextArea postTextArea;
    private JPanel imagePanel;  // Panel to hold the image labels
    private JButton addImageButton;
    private JButton submitButton;
    private JLabel titleLabel;
    private List<File> selectedImageFiles = new ArrayList<>();  // List to hold selected images
    private String placeholderText = "Write your post here...";


    public ContentCreationAreaView() {
        // Setup the frame
        setTitle("Create a New Post");
        setSize(1500, 800);
        setLocationRelativeTo(null);
        setBackground(new Color(255, 255, 255));
        setResizable(true);

        // Initialize components
        postTextArea = new JTextArea();
        postTextArea.setText("Write your post here...");
        postTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        postTextArea.setPreferredSize(new Dimension(600, 60));
        postTextArea.setWrapStyleWord(true);
        postTextArea.setLineWrap(true);
        postTextArea.setText(placeholderText);
        postTextArea.setForeground(Color.GRAY);  // Set initial text color to gray (for placeholder)
        postTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (postTextArea.getText().equals(placeholderText)) {
                    postTextArea.setText("");  // Clear placeholder text
                    postTextArea.setForeground(Color.BLACK);  // Change text color to black
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (postTextArea.getText().isEmpty()) {
                    postTextArea.setText(placeholderText);  // Restore placeholder text
                    postTextArea.setForeground(Color.GRAY);  // Change text color back to gray
                }
            }
        });


        addImageButton = new JButton("Add Image",12,14);
        addImageButton.setPreferredSize(new Dimension(300, 40));
        submitButton = new JButton("Submit Post",12,14);
        submitButton.setPreferredSize(new Dimension(300, 40));
        titleLabel = new JLabel("Create a Post", 12, GUIConstants.blue, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Initialize the image panel
        imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Ensure images align to the left

        // Add action listeners
        addImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImageFileChooser();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitPost();
            }
        });

        // Layout setup for fixed center alignment
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());  // Use BorderLayout for the main panel

        // Create a sub-panel to hold the title, textarea, image button, image panel, and submit button
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between title and text area
        contentPanel.add(postTextArea);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between text area and image button
        contentPanel.add(addImageButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between image button and image panel

        // Add image panel between the Add Image button and the Submit Post button
        JScrollPane scrollPane = new JScrollPane(imagePanel); // Adding scrollable area for images
        scrollPane.setPreferredSize(new Dimension(800, 1000));  // Adjust size based on need
        contentPanel.add(scrollPane);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between image panel and submit button
        contentPanel.add(submitButton);

        // Add the content panel to the center of the main panel
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private void openImageFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);  // Allow multiple files
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            for (File file : selectedFiles) {
                selectedImageFiles.add(file);
            }
            updateImageLabels();
        }
    }

    private void updateImageLabels() {
        imagePanel.removeAll();  // Clear existing images in the panel

        for (File file : selectedImageFiles) {
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());

            // Resize image to fit into a 100x100 box, while maintaining aspect ratio
            Image image = icon.getImage();
            int width = 800;
            int height = 1000;

            // Calculate aspect ratio
            double aspectRatio = (double) image.getWidth(null) / image.getHeight(null);
            if (aspectRatio > 1) {
                // If the image is wider than it is tall, scale by width
                height = (int) (width / aspectRatio);
            } else {
                // If the image is taller than it is wide, scale by height
                width = (int) (height * aspectRatio);
            }

            // Get the image scaled with preserved aspect ratio
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);

            // Create JLabel with the resized image
            javax.swing.JLabel imageLabel = new javax.swing.JLabel(icon);
            imageLabel.setPreferredSize(new Dimension(width, height)); // Set preferred size
            imagePanel.add(imageLabel);
        }

        imagePanel.revalidate();  // Refresh the panel to display new images
        imagePanel.repaint();
    }

    private void submitPost() {
        String postContent = postTextArea.getText();
        JOptionPane.showMessageDialog(this, "Post submitted successfully!");

        // Clear content after submission
        postTextArea.setText("");
        selectedImageFiles.clear();  // Clear the list of selected images
        updateImageLabels();// Remove images from the panel
        setVisible(true);
    }
}
