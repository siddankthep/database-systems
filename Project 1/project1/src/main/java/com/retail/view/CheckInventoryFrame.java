package com.retail.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CheckInventoryFrame extends JFrame {
    private JTable inventoryTable;
    private JTextField searchField;

    public CheckInventoryFrame() {
        setTitle("Check Inventory");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Sample data, you would usually fetch this from your database
        String[] columnNames = {"Product ID", "Product Name", "Stock Quantity"};
        Object[][] data = {
            {1, "Laptop", 50},
            {2, "Smartphone", 100},
            {3, "Coffee", 75},
            {4, "Noodles", 200},
            {5, "Cereal", 75},
            {6, "Desk Chair", 30},
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        inventoryTable = new JTable(model);
        
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        
        // Panel for search functionality
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().toLowerCase();
                filterTable(model, searchTerm);
            }
        });

        searchPanel.add(new JLabel("Search Product By Name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void filterTable(DefaultTableModel model, String searchTerm) {
        // Clear existing rows
        model.setRowCount(0);

        // Sample data for filtering, you would usually fetch this from your database
        Object[][] allData = {
            {1, "Laptop", 50},
            {2, "Smartphone", 100},
            {3, "Coffee", 75},
            {4, "Noodles", 200},
            {5, "Cereal", 75},
            {6, "Desk Chair", 30},
        };

        // Add rows that match the search term
        for (Object[] product : allData) {
            String productName = (String) product[1];
            if (productName.toLowerCase().contains(searchTerm)) {
                model.addRow(product);
            }
        }

        // If no products match, you can optionally show a message
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No products found matching: " + searchTerm);
        }
    }
}
