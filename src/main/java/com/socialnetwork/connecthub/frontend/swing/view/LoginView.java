package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;
import com.socialnetwork.connecthub.frontend.swing.constants.GUIConstants;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.LoginDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.JTextField;
import com.socialnetwork.connecthub.shared.exceptions.InvalidLoginException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LoginView extends JFrame {
    private String navigationHandlerType = "final";

    public LoginView(SocialNetworkAPI socialNetworkAPI) {
        super("Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(115, 0, 182, 0));

        JLabel title = new JLabel("Login", 40, GUIConstants.blue, Font.BOLD);
        title.setHorizontalAlignment(JLabel.CENTER);
        panel.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(null);
        center.setBorder(BorderFactory.createEmptyBorder(34, 315, 17, 315));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Create the email text field with flexible width
        JTextField email = new JTextField("Email");
        email.setMaximumSize(new Dimension(400, 40));
        email.setPreferredSize(new Dimension(400, 40));
        email.setMinimumSize(new Dimension(400, 40));
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        center.add(email, gbc);

        JTextField password = new JTextField("Password");
        password.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        center.add(password, gbc);

        JButton login = new JButton("Login", 12, 20);

        login.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {


                LoginDTO loginDTO = new LoginDTO(email.getText(), password.getText());
                if(email.getText().equals("Email"))
                    loginDTO.setEmail(null);
                if(password.getText().equals("Password"))
                    loginDTO.setPassword(null);

                try {
                    UserDTO userDTO = socialNetworkAPI.getUserAccountService().login(loginDTO);
                    NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToNewsFeedView(userDTO);
                    LoginView.this.dispose();
                } catch (InvalidLoginException ex) {
                    new Alert(ex.getMessage(), LoginView.this);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        center.add(login, gbc);

        panel.add(center, BorderLayout.CENTER);

        javax.swing.JLabel createAcc = new javax.swing.JLabel("<html>Don't have an account?<a href='#' style='color: blue; text-decoration: underline;'> Sign up</a></html>");
        createAcc.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                LoginView.this.dispose();
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToSignUpView();
            }
        });
        createAcc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAcc.setHorizontalAlignment(JLabel.CENTER);
        panel.add(createAcc, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setVisible(true);
    }
}
