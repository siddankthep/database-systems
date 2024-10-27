package com.retail.view;

import javax.swing.*;

import com.retail.model.services.CustomerService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreateCustomerFrame extends JFrame {
    private JTextField nameField, phoneField, addressField;
    private JButton createButton, cancelButton;
    private CustomerService customerService;

    public CreateCustomerFrame(CustomerService customerService) {
        this.customerService = customerService;
        setTitle("Create New Customer");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Labels and fields
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();

        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();

        // Buttons
        createButton = new JButton("Create");
        cancelButton = new JButton("Cancel");

        // Add components to panel
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(createButton);
        panel.add(cancelButton);

        // Add action listeners for buttons
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateCustomer();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the frame
            }
        });

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void handleCreateCustomer() {
        // Retrieve input from text fields
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        try {
            // Call the CustomerService to create the customer
            customerService.createCustomer(name, phone, address);
            JOptionPane.showMessageDialog(this, "Customer created successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close the frame after successful creation
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error creating customer: " + ex.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
