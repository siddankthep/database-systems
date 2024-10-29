package com.retail.view;

import javax.swing.*;

import com.retail.model.services.UserAccountService;
import com.retail.view.components.LabeledTextInput;

import java.awt.*;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private LabeledTextInput usernameInput;
    private LabeledTextInput passwordInput;
    private JButton loginButton;
    private UserAccountService userAccountService;

    public LoginFrame(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
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
        loginButton.addActionListener(new LoginButtonListener());
        this.add(loginButton, c);

    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            try {
                if (userAccountService.authenticate(username, password)) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Login successful!");
                    int role = userAccountService.getUserRole(username);
                    if (role == 1) {
                        new EmployeeMenu();
                    } else {
                        new ManagerMenu();
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Login failed. Please check your username and password.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(LoginFrame.this, "An error occurred while trying to login.");
            }
        }
    }
}
