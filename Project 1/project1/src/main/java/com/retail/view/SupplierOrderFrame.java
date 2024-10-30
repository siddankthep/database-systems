package com.retail.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.retail.controller.SupplierOrderController;

import java.awt.*;

public class SupplierOrderFrame extends JFrame {
    private JTextField productIdField;
    private JTextField quantityField;
    private JTable orderTable;
    private DefaultTableModel orderTableModel;
    private JLabel subtotalLabel;
    private JButton createOrderButton;
    private SupplierOrderController supplierOrderController;

    public SupplierOrderFrame() {
        this.supplierOrderController = new SupplierOrderController();
        setTitle("Create Supplier Order");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(550, 0);

        JPanel inputPanel = new JPanel();
        GroupLayout layout = new GroupLayout(inputPanel);
        inputPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Create components
        JLabel productIdLabel = new JLabel("Product ID:");
        productIdField = new JTextField();
        productIdField.setPreferredSize(new Dimension(50, 25));
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(50, 25));
        JButton addButton = new JButton("Add to Order");
        addButton.addActionListener(e -> supplierOrderController.addProductToOrder(productIdField, quantityField,
                orderTableModel, subtotalLabel));
        createOrderButton = new JButton("Create Order");
        createOrderButton.addActionListener(
                e -> supplierOrderController.createSupplierOrder(orderTableModel, subtotalLabel));

        // Set horizontal group
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(productIdLabel)
                        .addComponent(productIdField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(quantityLabel)
                        .addComponent(quantityField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(addButton)
                        .addComponent(createOrderButton));

        // Set vertical group
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(productIdLabel)
                        .addComponent(productIdField)
                        .addComponent(quantityLabel)
                        .addComponent(quantityField)
                        .addComponent(addButton)
                        .addComponent(createOrderButton));

        add(inputPanel, BorderLayout.NORTH);

        // Table to display items in the order
        String[] columnNames = { "Product ID", "Product Name", "Supplier Name", "Quantity", "Price", "Total" };
        orderTableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(orderTableModel);
        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Subtotal Panel
        JPanel subtotalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        subtotalLabel = new JLabel("Subtotal: $0.00");
        subtotalPanel.add(subtotalLabel);
        add(subtotalPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

}
