package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;
import com.socialnetwork.connecthub.frontend.swing.constants.GUIConstants;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.interfaces.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.SignUpDTO;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.JTextField;
import com.socialnetwork.connecthub.shared.exceptions.InvalidSignupException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

public class SignUpView extends JFrame {
    private SocialNetworkAPI socialNetworkAPI;
    private NavigationHandlerFactory navigationHandlerFactory;

    public SignUpView() {
        super("Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(53, 84, 76, 84));

        JLabel title = new JLabel("Sign Up", 40, GUIConstants.blue, Font.BOLD);
        title.setHorizontalAlignment(JLabel.CENTER);
        panel.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(null);
        center.setBorder(BorderFactory.createEmptyBorder(22, 231, 17, 231));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JTextField fullName = new JTextField("Full Name");
        fullName.setMaximumSize(new Dimension(400, 40));
        fullName.setPreferredSize(new Dimension(400, 40));
        fullName.setMinimumSize(new Dimension(400, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        center.add(fullName, gbc);

        JTextField email = new JTextField("Email");
        email.setMaximumSize(new Dimension(400, 40));
        email.setPreferredSize(new Dimension(400, 40));
        email.setMinimumSize(new Dimension(400, 40));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        center.add(email, gbc);

        JTextField password = new JTextField("Password");
        password.setMaximumSize(new Dimension(400, 40));
        password.setPreferredSize(new Dimension(400, 40));
        password.setMinimumSize(new Dimension(400, 40));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        center.add(password, gbc);

        JTextField confirmPassword = new JTextField("Confirm Password");
        confirmPassword.setMaximumSize(new Dimension(400, 40));
        confirmPassword.setPreferredSize(new Dimension(400, 40));
        confirmPassword.setMinimumSize(new Dimension(400, 40));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        center.add(confirmPassword, gbc);

        // Date Picker using JSpinner
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy");
        dateSpinner.setEditor(dateEditor);

        // Disable typing by making the editor non-editable
        JComponent editor = dateSpinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) editor).getTextField().setEditable(false);
        }

        dateSpinner.setPreferredSize(new Dimension(400, 40));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        center.add(dateSpinner, gbc);

        JButton signUpButton = new JButton("Sign Up", 12, 20);
        signUpButton.setPreferredSize(new Dimension(400, 40));
        signUpButton.setMaximumSize(new Dimension(400, 40));
        signUpButton.setMinimumSize(new Dimension(400, 40));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        center.add(signUpButton, gbc);

        signUpButton.addMouseListener(new MouseListener() {
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
                Date selectedDate = (Date) dateSpinner.getValue();

                SignUpDTO signUpDTO = new SignUpDTO();
                signUpDTO.setEmail(email.getText());
                signUpDTO.setUsername(fullName.getText());
                signUpDTO.setPassword(password.getText());
                signUpDTO.setDateOfBirth(new java.sql.Date(selectedDate.getTime()));

                try {
                    socialNetworkAPI.getUserAccountService().signup(signUpDTO);
                    navigationHandlerFactory.getNavigationHandler().goToLoginView();
                    SignUpView.this.dispose();
                } catch (InvalidSignupException ex) {
                    new Alert(ex.getMessage(), SignUpView.this);
                } catch (Exception ex) {
                    new Alert("An error occurred during signup. Please try again.", SignUpView.this);
                }
            }
        });

        panel.add(center, BorderLayout.CENTER);

        javax.swing.JLabel alreadyAccount = new javax.swing.JLabel("<html>Already have an account?<a href='#' style='color: blue; text-decoration: underline;'> Login</a></html>");
        alreadyAccount.addMouseListener(new MouseListener() {
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
                SignUpView.this.dispose();
                new LoginView();
            }
        });
        alreadyAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));
        alreadyAccount.setHorizontalAlignment(JLabel.CENTER);
        panel.add(alreadyAccount, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setVisible(true);
    }
}
