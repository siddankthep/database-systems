package com.retail.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerMenu extends JFrame {

    public ManagerMenu() {
        setTitle("Manager Menu");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton createEmployeeButton = new JButton("Create Employee Account");
        JButton checkInventoryButton = new JButton("Check Inventory");
        JButton supplierOrderButton = new JButton("Make Supplier Order");
        
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

        supplierOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SupplierOrderFrame();
            }
        });

        panel.add(createEmployeeButton);
        panel.add(checkInventoryButton);
        panel.add(supplierOrderButton);
        add(panel);

        setVisible(true);
    }
}
