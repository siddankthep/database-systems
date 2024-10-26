package com.retail.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RetailApp extends JFrame {

    public RetailApp() {
        setTitle("Retail Store Management");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton managerButton = new JButton("Manager Menu");
        JButton employeeButton = new JButton("Employee Menu");

        managerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManagerMenu();
            }
        });

        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmployeeMenu();
            }
        });

        panel.add(managerButton);
        panel.add(employeeButton);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RetailApp app = new RetailApp();
            app.setVisible(true);
        });
    }
}
