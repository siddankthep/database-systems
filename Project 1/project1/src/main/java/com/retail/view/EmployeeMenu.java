package com.retail.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeMenu extends JFrame {

    public EmployeeMenu(JFrame loginFrame) {
        setTitle("Employee Menu");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton createOrderButton = new JButton("Create Order");
        JButton createCustomerButton = new JButton("Create Customer");
        JButton logoutButton = new JButton("Logout");

        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateOrderFrame();
            }
        });

        createCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateCustomerFrame();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                loginFrame.setVisible(true);
            }
        });

        panel.add(createOrderButton);
        panel.add(createCustomerButton);
        panel.add(logoutButton);
        add(panel);

        setVisible(true);
    }
}
