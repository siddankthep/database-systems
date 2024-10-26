package com.retail.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateEmployeeFrame extends JFrame {
    private JTextField usernameField;
    private JTextField passwordField;

    public CreateEmployeeFrame() {
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
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement create employee logic here
                String username = usernameField.getText();
                String password = passwordField.getText();
                // Call your service or DAO to save the employee
                JOptionPane.showMessageDialog(CreateEmployeeFrame.this, "Employee created: " + username);
                dispose();
            }
        });

        panel.add(createButton);
        add(panel);

        setVisible(true);
    }
}
