package com.retail.controller.Manager;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.retail.model.dao.UserAccountDAO;
import com.retail.model.entities.UserAccount;
import com.retail.model.services.UserAccountService;

public class CreateEmployeeController {
    private UserAccountService userAccountService;

    public CreateEmployeeController() {
        this.userAccountService = new UserAccountService(new UserAccountDAO());
    }

    public void createEmployee(JTextField usernameField, JTextField passwordField, JFrame frame) {

        String username = usernameField.getText();
        String password = passwordField.getText();

        UserAccount user = new UserAccount(username, password, 1);
        try {
            userAccountService.createUser(user);
            JOptionPane.showMessageDialog(frame, "User created: " + username);
            frame.dispose();
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(frame,
                    "Error creating employee: " + e1.getMessage());
            e1.printStackTrace();
        }

    }
}
