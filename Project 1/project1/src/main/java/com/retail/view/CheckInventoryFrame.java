package com.retail.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

import com.retail.model.entities.Product;
import com.retail.model.services.ProductService;

public class CheckInventoryFrame extends JFrame {
    private JTable inventoryTable;
    private JTextField searchField;
    private ProductService productService;
    private List<Product> products;
    private Object[][] data;

    public CheckInventoryFrame(ProductService productService) {
        this.productService = productService;
        setTitle("Check Inventory");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sample data, you would usually fetch this from your database
        String[] columnNames = { "Product ID", "Product Name", "Unit Price", "Stock Quantity" };
        try {
            products = productService.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }

        data = new Object[products.size()][4];

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);

            Object productID = (Object) product.getProductId();
            Object productName = (Object) product.getProductName();
            Object price = (Object) product.getPrice();
            Object stockQuantity = (Object) product.getStockQuantity();

            Object[] row = { productID, productName, price, stockQuantity };
            data[i] = row;
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        inventoryTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(inventoryTable);

        // Panel for search functionality
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTableSearch(model, searchField.getText().toLowerCase());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTableSearch(model, searchField.getText().toLowerCase());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTableSearch(model, searchField.getText().toLowerCase());
            }
        });

        searchPanel.add(new JLabel("Search Product By Name:"));
        searchPanel.add(searchField);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        JButton makeSupplierOrderButton = new JButton("Make Supplier Order");
        makeSupplierOrderButton.addActionListener(e -> new SupplierOrderFrame());
        add(makeSupplierOrderButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void filterTableSearch(DefaultTableModel model, String searchTerm) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        inventoryTable.setRowSorter(sorter);

        if (searchTerm.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm));
        }
    }
}
