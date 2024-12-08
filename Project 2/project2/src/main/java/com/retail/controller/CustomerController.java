package com.retail.controller;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.retail.model.dao.CustomerDAO;
import com.retail.model.dao.UserAccountDAO;
import com.retail.model.entities.UserAccount;
import com.retail.model.services.CustomerService;
import com.retail.model.services.UserAccountService;

public class CustomerController {
    private CustomerService customerService;
    private UserAccountService userAccountService;

    public CustomerController() {
        this.customerService = new CustomerService(new CustomerDAO());
        this.userAccountService = new UserAccountService(new UserAccountDAO());
    }

    public void handleCreateCustomer(JTextField nameField, JTextField phoneField, JTextField addressField,
            JFrame frame) {
        // Retrieve input from text fields
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        try {
            // Call the CustomerService to create the customer
            customerService.createCustomerSQL(name, phone, address);
            userAccountService.createUser(new UserAccount(phone, "password", 3)); // Create a user account with role 3
                                                                                  // (customer)
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
