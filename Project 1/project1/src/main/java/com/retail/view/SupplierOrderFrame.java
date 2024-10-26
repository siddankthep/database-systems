package com.retail.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupplierOrderFrame extends JFrame {
    private JTextField supplierIdField;
    private JTextField productIdField;
    private JTextField quantityField;

    public SupplierOrderFrame() {
        setTitle("Create Supplier Order");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Supplier ID:"));
        supplierIdField = new JTextField();
        panel.add(supplierIdField);

        panel.add(new JLabel("Product ID:"));
        productIdField = new JTextField();
        panel.add(productIdField);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        JButton createButton = new JButton("Create Order");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement supplier order logic here
                String supplierId = supplierIdField.getText();
                String productId = productIdField.getText();
                String quantity = quantityField.getText();
                // Call your service or DAO to save the supplier order
                JOptionPane.showMessageDialog(SupplierOrderFrame.this, "Supplier Order created for Supplier ID: " + supplierId);
                dispose();
            }
        });

        panel.add(createButton);
        add(panel);

        setVisible(true);
    }
}
