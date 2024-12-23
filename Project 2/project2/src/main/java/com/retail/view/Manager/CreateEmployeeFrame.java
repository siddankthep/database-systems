package com.retail.view.Manager;

import javax.swing.*;

import com.retail.controller.Manager.CreateEmployeeController;

import java.awt.*;

public class CreateEmployeeFrame extends JFrame {
    private JTextField usernameField;
    private JTextField passwordField;
    private CreateEmployeeController createEmployeeController;

    public CreateEmployeeFrame() {
        this.createEmployeeController = new CreateEmployeeController();
        setTitle("Create Employee");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        panel.add(passwordField);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> createEmployeeController.createEmployee(usernameField, passwordField,
                CreateEmployeeFrame.this));

        panel.add(createButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);

        add(panel);

        setVisible(true);
    }
}
