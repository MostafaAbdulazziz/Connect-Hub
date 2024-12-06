package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.frontend.swing.components.JButton;

public class LoginView extends View {
    JButton loginButton;
    JButton signUpButton;
    public LoginView() {
        loginButton = new JButton("Login",12,22);
        signUpButton = new JButton("Sign Up",12,22);
        loginButton.setBounds(100, 100, 100, 50);
        signUpButton.setBounds(100, 200, 100, 50);
        add(loginButton);
        add(signUpButton);
    }
}