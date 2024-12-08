package com.retail.view.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import com.retail.model.entities.Product;

public class PaymentFrame extends JFrame {
    private ArrayList<Product> cart;

    public PaymentFrame(ArrayList<Product> cart) {
        this.cart = cart;

        setTitle("Payment");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the layout
        setLayout(new BorderLayout());

        // Add the product table
        add(createProductTable(), BorderLayout.CENTER);

        // Add the subtotal field
        add(createSubtotalPanel(), BorderLayout.SOUTH);
    }

    private JScrollPane createProductTable() {
        // Define table columns
        String[] columnNames = {"Product Name", "Price", "Quantity", "Total"};

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        // Populate the table with cart data
        for (Product product : cart) {
            tableModel.addRow(new Object[]{
                product.getProductName(),
                product.getPrice(),
                1,  // Default quantity (you can enhance this if quantity tracking is added)
                product.getPrice()  // Total = price * quantity
            });
        }

        // Disable editing in the table
        table.setEnabled(false);

        // Return the table wrapped in a scroll pane
        return new JScrollPane(table);
    }

    private JPanel createSubtotalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Calculate subtotal
        double subtotal = cart.stream().mapToDouble(Product::getPrice).sum();

        // Add subtotal label
        JLabel subtotalLabel = new JLabel("Subtotal: $" + String.format("%.2f", subtotal));
        subtotalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        panel.add(subtotalLabel);
        return panel;
    }

    
}
