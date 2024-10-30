package com.retail.controller;

import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.SupplierDAO;
import com.retail.model.dao.SupplierOrderDAO;
import com.retail.model.entities.Product;
import com.retail.model.entities.Supplier;
import com.retail.model.services.ProductService;
import com.retail.model.services.SupplierOrderService;
import com.retail.model.services.SupplierService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.Date;

public class SupplierOrderController {
    private SupplierOrderService supplierOrderService;
    private ProductService productService;
    private SupplierService supplierService;
    private int supplierId = -1;

    public SupplierOrderController() {
        this.supplierOrderService = new SupplierOrderService(new SupplierOrderDAO());
        this.productService = new ProductService(new ProductDAO());
        this.supplierService = new SupplierService(new SupplierDAO());
    }

    public void createSupplierOrder(DefaultTableModel orderTableModel, JLabel subtotalLabel) {
        if (orderTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Please add products to the order.");
                return;
            }

            try {

                // Calculate total amount
                String[] subtotalStrings = subtotalLabel.getText().split("\\$");
                String subtotalString = subtotalStrings[subtotalStrings.length - 1];
                Double totalAmount = Double.parseDouble(subtotalString);

                int supplierOrderId = supplierOrderService.createSupplierOrder(supplierId, new Date(), totalAmount);

                // Process each item in the order table
                for (int i = 0; i < orderTableModel.getRowCount(); i++) {
                    int productId = (int) orderTableModel.getValueAt(i, 0);
                    int quantity = (int) orderTableModel.getValueAt(i, 3);

                    // Increase stock quantity in product
                    Product product = productService.getProductById(productId);
                    product.setStockQuantity(product.getStockQuantity() + quantity);
                    productService.updateProduct(product);

                    supplierOrderService.addSupplierOrderDetail(supplierOrderId, productId,
                            quantity);
                }

                JOptionPane.showMessageDialog(null, "Order created successfully!");

                // Clear the order table and reset the subtotal
                orderTableModel.setRowCount(0);
                totalAmount = 0.0;
                updateSubtotal(orderTableModel, subtotalLabel);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error creating order: "
                        + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Order Details",
                        JOptionPane.ERROR_MESSAGE);
            }
    }

    public void updateSubtotal(DefaultTableModel orderTableModel, JLabel subtotalLabel) {
        double subtotal = 0.0;
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            subtotal += (double) orderTableModel.getValueAt(i, 5);
        }
        subtotalLabel.setText(String.format("Subtotal: $%.2f", subtotal));
    }

    public void addProductToOrder(JTextField productIdField, JTextField quantityField, DefaultTableModel orderTableModel, JLabel subtotalLabel) {
        try {
                int productId = Integer.parseInt(productIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                Product product = productService.getProductById(productId);
                if (product == null) {
                    JOptionPane.showMessageDialog(null, "Product not found.");
                    return;
                }

                // Fetch product details from the service
                String productName = product.getProductName(); // This should return the product
                                                               // name
                double price = product.getPrice(); // This should return the product price

                double total = Math.round(price * quantity * 100.0) / 100.0;

                // Fetch supplier details
                int currentSupplierId = product.getSupplierId();
                Supplier supplier = supplierService.getSupplierById(currentSupplierId);
                String supplierName = supplier.getSupplierName();

                // Check if the supplier ID is the same as the first supplier ID
                if (supplierId == -1) {
                    // First supplier ID
                    supplierId = currentSupplierId;
                } else if (supplierId != currentSupplierId) {
                    // Different supplier ID found
                    JOptionPane.showMessageDialog(null,
                            "Error: All products in the order must be from the same supplier.",
                            "Supplier Mismatch",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add row to the table
                System.out.println("Product ID: " + productId + ", Product Name: " + productName + ", Supplier Name: "
                        + supplierName + ", Quantity: " + quantity + ", Price: " + price + ", Total: " + total);
                Object[] row = { productId, productName, supplierName, quantity, price, total };
                orderTableModel.addRow(row);

                // Update the subtotal
                updateSubtotal(orderTableModel, subtotalLabel); // TODO: Check if this works

                // Clear input fields
                productIdField.setText("");
                quantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Please enter valid numbers for Product ID and Quantity.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error fetching product details: " + ex.getMessage(), "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
    }
}