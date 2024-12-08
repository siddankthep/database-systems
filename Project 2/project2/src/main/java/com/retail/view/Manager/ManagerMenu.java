package com.retail.view.Manager;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerMenu extends JFrame {

    public ManagerMenu(JFrame loginFrame) {
        setTitle("Manager Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton createEmployeeButton = new JButton("Create Employee Account");
        JButton checkInventoryButton = new JButton("Check Inventory");
        JButton logoutButton = new JButton("Logout");

        createEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateEmployeeFrame();
            }
        });

        checkInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CheckInventoryFrame();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                loginFrame.setVisible(true);
            }
        });

        panel.add(createEmployeeButton);
        panel.add(checkInventoryButton);
        panel.add(logoutButton);
        add(panel);

        setVisible(true);
    }
}
