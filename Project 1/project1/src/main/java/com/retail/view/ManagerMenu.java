package com.retail.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerMenu extends JFrame {

    public ManagerMenu() {
        setTitle("Manager Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton createEmployeeButton = new JButton("Create Employee Account");
        JButton checkInventoryButton = new JButton("Check Inventory");

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

        panel.add(createEmployeeButton);
        panel.add(checkInventoryButton);
        add(panel);

        setVisible(true);
    }
}
