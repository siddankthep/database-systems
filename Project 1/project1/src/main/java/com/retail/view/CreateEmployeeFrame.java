package com.retail.view;

import javax.swing.*;

import com.retail.model.entities.UserAccount;
import com.retail.model.services.UserAccountService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

public class CreateEmployeeFrame extends JFrame {
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField roleField;
    private UserAccountService userAccountService;

    public CreateEmployeeFrame(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
        setTitle("Create Employee");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        panel.add(passwordField);

        panel.add(new JLabel("RoleID:"));
        roleField = new JTextField();
        panel.add(roleField);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement create employee logic here
                String username = usernameField.getText();
                String password = passwordField.getText();
                int roleID = Integer.parseInt(roleField.getText());
                Date createdAt = new Date();
                Date lastLogin = null;

                UserAccount user = new UserAccount(0, username, password, roleID, createdAt, lastLogin);
                try {
                    userAccountService.createUser(user);
                    JOptionPane.showMessageDialog(CreateEmployeeFrame.this, "Employee created: " + username);
                    dispose();
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(CreateEmployeeFrame.this,
                            "Error creating employee: " + e1.getMessage());
                    e1.printStackTrace();
                }

            }
        });

        panel.add(createButton);
        add(panel);

        setVisible(true);
    }
}
