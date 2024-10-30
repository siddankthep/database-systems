package com.retail.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeMenu extends JFrame {

    public EmployeeMenu() {
        setTitle("Employee Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton createOrderButton = new JButton("Create Order");
        JButton createCustomerButton = new JButton("Create Customer");

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

        panel.add(createOrderButton);
        panel.add(createCustomerButton);
        add(panel);

        setVisible(true);
    }
}
