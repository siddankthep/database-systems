package com.retail.controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.retail.model.dao.UserAccountDAO;
import com.retail.model.services.UserAccountService;
import com.retail.view.LoginFrame;
import com.retail.view.Customer.CustomerMenu;
import com.retail.view.Employee.EmployeeMenu;
import com.retail.view.Manager.ManagerMenu;
import com.retail.view.components.LabeledTextInput;

public class LoginController {
    private UserAccountService userAccountService;
    private LoginFrame loginFrame;

    public LoginController(LoginFrame loginFrame) {
        this.userAccountService = new UserAccountService(new UserAccountDAO());
        this.loginFrame = loginFrame;
    }

    public void authenticate(LabeledTextInput usernameInput, LabeledTextInput passwordInput, JFrame frame) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        try {
            if (userAccountService.authenticate(username, password)) {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                int role = userAccountService.getUserRole(username);
                if (role == 1) {
                    new EmployeeMenu(loginFrame);
                } else if (role == 2) {
                    new ManagerMenu(loginFrame);
                } else {
                    new CustomerMenu(loginFrame, username);
                }
                frame.setVisible(false);
                loginFrame.getUsernameInput().setText("");
                loginFrame.getPasswordInput().setText("");
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Login failed. Please check your username and password.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while trying to login.");
        }
    }
}
