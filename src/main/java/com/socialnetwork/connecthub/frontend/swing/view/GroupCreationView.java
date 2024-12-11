package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.frontend.swing.constants.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.JTextField;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

public class GroupCreationView extends JFrame {

    private JTextField groupNameField;
    private JTextField descriptionField;
    private JLabel groupImageLabel;
    private JButton uploadButton;
    private JButton createButton;
    private File selectedImage;
    private UserDTO user;
    private SocialNetworkAPI socialNetworkAPI;
    private String navigationHandlerType = "final";

    public GroupCreationView(SocialNetworkAPI socialNetworkAPI, UserDTO user) {
        this.socialNetworkAPI = socialNetworkAPI;
        this.user = user;
        setTitle("Group Creation");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        getContentPane().setBackground(Color.WHITE);

        // Main content panel with padding and background color
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Group Name Section
        JLabel nameLabel = new JLabel("Group Name", JLabel.CENTER, GUIConstants.blue, Font.BOLD);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        groupNameField = new JTextField("Enter group name");
        groupNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        groupNameField.setForeground(GUIConstants.black);
        groupNameField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        groupNameField.setCaretColor(GUIConstants.black);

        // Group Description Section
        JLabel descriptionLabel = new JLabel("Description", JLabel.CENTER, GUIConstants.blue, Font.BOLD);
        descriptionLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        descriptionField = new JTextField("Enter group description");
        descriptionField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionField.setForeground(GUIConstants.black);
        descriptionField.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        descriptionField.setCaretColor(GUIConstants.black);

        // Group Photo Section
        JLabel photoLabel = new JLabel("Group Photo", JLabel.CENTER, GUIConstants.blue, Font.BOLD);
        photoLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        groupImageLabel = new JLabel("No photo selected", JLabel.CENTER, GUIConstants.blue, Font.PLAIN);
        groupImageLabel.setPreferredSize(new Dimension(120, 120));
        groupImageLabel.setBackground(GUIConstants.blue);
        groupImageLabel.setOpaque(true);
        groupImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //groupImageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Upload Button for Photo
        uploadButton = new JButton("Upload Photo", 20, 14);
        uploadButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        uploadButton.setBackground(GUIConstants.blue);
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        uploadButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadPhoto();
            }
        });

        // Create Group Button
        createButton = new JButton("Create Group", 20, 14);
        createButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createButton.setBackground(GUIConstants.blue);
        createButton.setForeground(Color.WHITE);
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGroup();
            }
        });

        // Adding components to the main panel
        mainPanel.add(nameLabel);
        mainPanel.add(groupNameField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(descriptionLabel);
        mainPanel.add(descriptionField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(photoLabel);
        mainPanel.add(groupImageLabel);
        mainPanel.add(uploadButton);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(createButton);

        // Adding the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Method to handle photo upload
    private void uploadPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a Group Photo");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImage = fileChooser.getSelectedFile();
            ImageIcon imageIcon = new ImageIcon(selectedImage.getPath());
            Image image = imageIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            groupImageLabel.setIcon(new ImageIcon(image));
        }
    }

    // Method to handle group creation
    private void createGroup() {
        String groupName = groupNameField.getText();
        String description = descriptionField.getText();

        if (groupName.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedImage == null) {
            JOptionPane.showMessageDialog(this, "Please upload a group photo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Here, you can process the creation of the group, e.g., save the data to a database or a file.
        JOptionPane.showMessageDialog(this, "Group created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose(); // Close the window after creation
    }
}
