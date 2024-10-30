package com.retail.view;

import javax.swing.*;

import com.retail.controller.LoginController;
import com.retail.view.components.LabeledTextInput;

import java.awt.*;

public class LoginFrame extends JFrame {
    private LabeledTextInput usernameInput;
    private LabeledTextInput passwordInput;
    private JButton loginButton;
    private LoginController loginController;

    public LoginFrame() {
        this.loginController = new LoginController();
        this.setTitle("Login");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        c.insets = new Insets(5, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        usernameInput = new LabeledTextInput("Username");
        this.add(usernameInput, c);

        c.gridx = 0;
        c.gridy = 1;
        passwordInput = new LabeledTextInput("Password");
        this.add(passwordInput, c);

        c.gridx = 0;
        c.gridy = 2;
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> loginController.authenticate(usernameInput, passwordInput, this));
        this.add(loginButton, c);

    }

}
