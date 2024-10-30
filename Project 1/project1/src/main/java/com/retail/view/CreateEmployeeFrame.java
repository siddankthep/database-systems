package com.retail.view;

import javax.swing.*;

import com.retail.controller.CreateEmployeeController;

import java.awt.*;

public class CreateEmployeeFrame extends JFrame {
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField roleField;
    private CreateEmployeeController createEmployeeController;

    public CreateEmployeeFrame() {
        this.createEmployeeController = new CreateEmployeeController();
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
        createButton.addActionListener(e -> createEmployeeController.createEmployee(usernameField, passwordField,
                roleField, CreateEmployeeFrame.this));

        panel.add(createButton);
        add(panel);

        setVisible(true);
    }
}
