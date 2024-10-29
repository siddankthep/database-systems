package com.retail.main;

import javax.swing.*;
import com.retail.model.dao.UserAccountDAO;
import com.retail.model.services.UserAccountService;
import com.retail.view.LoginFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame(new UserAccountService(new UserAccountDAO()));
            login.setVisible(true);
        });
    }
}