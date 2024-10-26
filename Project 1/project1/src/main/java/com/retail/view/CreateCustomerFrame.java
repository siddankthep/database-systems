package com.retail.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateCustomerFrame extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;

    public CreateCustomerFrame() {
        setTitle("Create Customer");
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        JButton createButton = new JButton("Create Customer");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement create customer logic here
                String name = nameField.getText();
                String phone = phoneField.getText();
                String address = addressField.getText();
                // Call your service or DAO to save the customer
                JOptionPane.showMessageDialog(CreateCustomerFrame.this, "Customer created: " + name);
                dispose();
            }
        });

        panel.add(createButton);
        add(panel);

        setVisible(true);
    }
}
