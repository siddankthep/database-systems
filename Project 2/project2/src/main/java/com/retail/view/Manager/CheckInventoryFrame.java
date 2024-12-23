package com.retail.view.Manager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.retail.controller.Manager.InventoryController;

import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CheckInventoryFrame extends JFrame {
    private JTable inventoryTable;
    private JTextField searchField;
    private InventoryController inventoryController;

    public CheckInventoryFrame() {
        this.inventoryController = new InventoryController();
        setTitle("Check Inventory");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(700, 400);

        String[] columnNames = { "Product ID", "Product Name", "Supplier Name", "Unit Price", "Stock Quantity" };
        DefaultTableModel model = new DefaultTableModel(inventoryController.fetchProductData(), columnNames);
        inventoryTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(inventoryTable);

        // Panel for search functionality
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                inventoryController.filterTableSearch(model, searchField.getText().toLowerCase(), inventoryTable);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                inventoryController.filterTableSearch(model, searchField.getText().toLowerCase(), inventoryTable);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                inventoryController.filterTableSearch(model, searchField.getText().toLowerCase(), inventoryTable);
            }
        });

        searchPanel.add(new JLabel("Search Product By Name:"));
        searchPanel.add(searchField);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton makeSupplierOrderButton = new JButton("Make Supplier Order");
        makeSupplierOrderButton
                .addActionListener(e -> new SupplierOrderFrame());

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> inventoryController.refreshTable(model));

        buttonPanel.add(makeSupplierOrderButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

}
