package com.retail.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateOrderFrame extends JFrame {
    private JTextField productIdField;
    private JTextField quantityField;
    private JTable orderTable;
    private DefaultTableModel orderTableModel;
    private JLabel subtotalLabel;

    public CreateOrderFrame() {
        setTitle("Create Order");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Input Panel for Product ID and Quantity
        JPanel inputPanel = new JPanel(new GridLayout(1, 5));
        
        inputPanel.add(new JLabel("Product ID:"));
        productIdField = new JTextField();
        inputPanel.add(productIdField);
        
        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);
        
        JButton addButton = new JButton("Add to Order");
        addButton.addActionListener(new AddButtonListener());
        inputPanel.add(addButton);
        
        add(inputPanel, BorderLayout.NORTH);

        // Table to display items in the order
        String[] columnNames = {"Product ID", "Product Name", "Quantity", "Price", "Total"};
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

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int productId = Integer.parseInt(productIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                
                // Retrieve product information (for example purposes, we'll use hardcoded data)
                // In a real scenario, fetch product info from the database using productId
                String productName = "Sample Product"; // Sample data, replace with actual data
                double price = 10.00; // Sample price, replace with actual price from database
                
                double total = price * quantity;
                
                // Add row to the table
                Object[] row = {productId, productName, quantity, price, total};
                orderTableModel.addRow(row);

                // Update the subtotal
                updateSubtotal();

                // Clear input fields
                productIdField.setText("");
                quantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CreateOrderFrame.this, "Please enter valid numbers for Product ID and Quantity.");
            }
        }
    }

    private void updateSubtotal() {
        double subtotal = 0.0;
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            subtotal += (double) orderTableModel.getValueAt(i, 4);
        }
        subtotalLabel.setText(String.format("Subtotal: $%.2f", subtotal));
    }
}
