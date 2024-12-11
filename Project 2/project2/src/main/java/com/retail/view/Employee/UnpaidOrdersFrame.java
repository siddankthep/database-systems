package com.retail.view.Employee;

import javax.swing.*;

import com.retail.controller.Employee.UnpaidOrdersController;

import java.awt.*;

public class UnpaidOrdersFrame extends JFrame {
    private UnpaidOrdersController controller;
    private JTable unpaidOrdersTable;

    public UnpaidOrdersFrame() {
        controller = new UnpaidOrdersController();
        setTitle("Unpaid Orders");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the table to display unpaid orders
        unpaidOrdersTable = controller.createUnpaidOrdersTable();

        // Add components to the frame
        add(new JScrollPane(unpaidOrdersTable), BorderLayout.CENTER);

        setVisible(true);
    }

    public void updateTable() {
        unpaidOrdersTable = controller.createUnpaidOrdersTable();
    }

}
