package com.retail.controller;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.retail.model.dao.CustomerDAO;
import com.retail.model.services.CustomerService;

public class CustomerController {
    private CustomerService customerService;

    public CustomerController() {
        this.customerService = new CustomerService(new CustomerDAO());
    }

    public void handleCreateCustomer(JTextField nameField, JTextField phoneField, JTextField addressField, JFrame frame) {
        // Retrieve input from text fields
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        try {
            // Call the CustomerService to create the customer
            customerService.createCustomer(name, phone, address);
            JOptionPane.showMessageDialog(null, "Customer created successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            frame.dispose(); // Close the frame after successful creation
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error creating customer: " + ex.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
