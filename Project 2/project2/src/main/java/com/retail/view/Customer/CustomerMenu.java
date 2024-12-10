package com.retail.view.Customer;

import javax.swing.*;

import com.retail.view.LoginFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerMenu extends JFrame {

    public CustomerMenu(LoginFrame loginFrame, String phone) {
        setTitle("Manager Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton checkStoreButton = new JButton("Check Storefront");
        JButton checkHistoryButton = new JButton("View Order History");
        JButton logoutButton = new JButton("Logout");

        checkStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StorefrontFrame(phone);
            }
        });

        checkHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderHistoryFrame(phone);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                loginFrame.setVisible(true);
            }
        });

        panel.add(checkStoreButton);
        panel.add(checkHistoryButton);
        panel.add(logoutButton);
        add(panel);

        setVisible(true);
    }
}
