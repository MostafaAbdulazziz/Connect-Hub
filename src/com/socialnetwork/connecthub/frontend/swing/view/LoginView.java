package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;
import com.socialnetwork.connecthub.frontend.swing.constants.GUIConstants;
import com.socialnetwork.connecthub.shared.dto.LoginDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.JTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LoginView extends JFrame {
    private SocialNetworkAPI socialNetworkAPI;  // Add this to access backend services

    public LoginView() {
        super("Login");  // Set window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(115, 0, 182, 0));

        JLabel title = new JLabel("Login", 40, GUIConstants.blue, Font.BOLD);
        title.setHorizontalAlignment(JLabel.CENTER);
        panel.add(title, BorderLayout.NORTH);

        // Use GridBagLayout for more control over component placement
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(null);
        center.setBorder(BorderFactory.createEmptyBorder(34, 315, 17, 315));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);  // Add margin between components

        // Create the email text field with flexible width
        JTextField email = new JTextField("Email");
        email.setMaximumSize(new Dimension(400, 40));  // Set maximum width to fill available space
        email.setPreferredSize(new Dimension(400, 40));  // Set preferred width
        email.setMinimumSize(new Dimension(400, 40));  // Set minimum width
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        center.add(email, gbc);

        // Create the password text field with flexible width
        JTextField password = new JTextField("Password");
        password.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));  // Set maximum width to fill available space
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        center.add(password, gbc);

        // Create the login button
        JButton login = new JButton("Login", 12, 20);

        login.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseClicked(MouseEvent e) {
                if (email.getText().isEmpty()) {
                    new Alert("Email cannot be empty", LoginView.this);
                    return;
                }
                if (password.getText().isEmpty()) {
                    new Alert("Password cannot be empty", LoginView.this);
                    return;
                }

                // Prepare the DTO with user credentials
                LoginDTO loginDTO = new LoginDTO();
                //loginDTO.setEmail(email.getText());
                //loginDTO.setPassword(password.getText());

                // Call the backend service to perform login
                boolean isLoggedIn = socialNetworkAPI.getUserAccountService().login(loginDTO);

                if (isLoggedIn) {
                    UserDTO userDTO = socialNetworkAPI.getUserAccountService().getUserById(loginDTO.getEmail()); // Assuming email is used as userId
                    new NewsFeedView();
                    LoginView.this.dispose();  // Close current LoginView window
                } else {
                    new Alert("Incorrect email or password", LoginView.this);
                }
            }
        });

        // Add the login button with margin
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        center.add(login, gbc);

        panel.add(center, BorderLayout.CENTER);

        // Modify the "Create new one" label to make it look like a link
        javax.swing.JLabel createAcc = new javax.swing.JLabel("<html>Don't have an account?<a href='#' style='color: blue; text-decoration: underline;'> Sign up</a></html>");
        createAcc.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseClicked(MouseEvent e) {
                LoginView.this.dispose();  // Close the login window when sign up is clicked
                new SignUpView();  // Open the SignUpView
            }
        });
        createAcc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAcc.setHorizontalAlignment(JLabel.CENTER);
        panel.add(createAcc, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setVisible(true);  // Make the window visible
    }
}
