package com.retail.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.sql.SQLException;

import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.SupplierDAO;
import com.retail.model.dao.SupplierOrderDAO;
import com.retail.model.entities.Product;
import com.retail.model.entities.Supplier;
import com.retail.model.services.ProductService;
import com.retail.model.services.SupplierOrderService;
import com.retail.model.services.SupplierService;

public class CheckInventoryFrame extends JFrame {
    private JTable inventoryTable;
    private JTextField searchField;
    private ProductService productService;
    private SupplierService supplierService;
    private List<Product> products;
    private Object[][] data;

    public CheckInventoryFrame(ProductService productService, SupplierService supplierService) {
        this.supplierService = supplierService;
        this.productService = productService;
        setTitle("Check Inventory");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sample data, you would usually fetch this from your database
        String[] columnNames = { "Product ID", "Product Name", "Supplier Name", "Unit Price", "Stock Quantity" };
        try {
            products = productService.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }

        data = new Object[products.size()][4];

        for (int i = 0; i < products.size(); i++) {
            try {

                Product product = products.get(i);

                Object productID = (Object) product.getProductId();
                Object productName = (Object) product.getProductName();
                Supplier supplier = supplierService.getSupplierById(product.getSupplierId());
                Object supplierName = (Object) supplier.getSupplierName();
                Object price = (Object) product.getPrice();
                Object stockQuantity = (Object) product.getStockQuantity();

                Object[] row = { productID, productName, supplierName, price, stockQuantity };
                data[i] = row;
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching products: " + e.getMessage());
            }
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

        JPanel buttonPanel = new JPanel();
        JButton makeSupplierOrderButton = new JButton("Make Supplier Order");
        makeSupplierOrderButton
                .addActionListener(e -> new SupplierOrderFrame(new SupplierOrderService(new SupplierOrderDAO()),
                        new ProductService(new ProductDAO()), new SupplierService(new SupplierDAO())));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            try {
                products = productService.getAllProducts();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            data = new Object[products.size()][4];

            for (int i = 0; i < products.size(); i++) {
                try {

                    Product product = products.get(i);
                    
                    Object productID = (Object) product.getProductId();
                    Object productName = (Object) product.getProductName();
                    Supplier supplier = supplierService.getSupplierById(product.getSupplierId());
                    Object supplierName = (Object) supplier.getSupplierName();
                    Object price = (Object) product.getPrice();
                    Object stockQuantity = (Object) product.getStockQuantity();
                    
                    Object[] row = { productID, productName, supplierName, price, stockQuantity };
                    data[i] = row;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error fetching products: " + ex.getMessage());
                }
            }

            model.setDataVector(data, columnNames);
            model.fireTableDataChanged();
        });
        buttonPanel.add(makeSupplierOrderButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

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

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         new CheckInventoryFrame(new ProductService(new ProductDAO()), new SupplierService(new SupplierDAO()));
    //     });
    // }
}
