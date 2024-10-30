package com.retail.controller;

import com.retail.model.dao.CustomerDAO;
import com.retail.model.dao.OrderDAO;
import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.ShipperDAO;
import com.retail.model.entities.OrderDetails;
import com.retail.model.entities.Product;
import com.retail.model.entities.Shipper;
import com.retail.model.services.CustomerService;
import com.retail.model.services.OrderService;
import com.retail.model.services.ProductService;
import com.retail.model.services.ShipperService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.Date;

public class OrderController {
    private OrderService orderService;
    private ProductService productService;
    private ShipperService shipperService;
    private CustomerService customerService;

    public OrderController() {
        this.orderService = new OrderService(new OrderDAO());
        this.productService = new ProductService(new ProductDAO());
        this.shipperService = new ShipperService(new ShipperDAO());
        this.customerService = new CustomerService(new CustomerDAO());
    }

    public int assignShipper(JLabel assignedShipperLabel) {
        try {
            int shipperId = shipperService.getRandomShipperId();
            Shipper shipper = shipperService.getShipperById(shipperId);
            String shipperName = shipper.getShipperName();
            String assignedShipper = "Auto-Assigned Shipper: " + shipperName;
            assignedShipperLabel.setText(assignedShipper);
            return shipperId;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error assigning shipper: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
    }

    public void createOrder(DefaultTableModel orderTableModel, JLabel subtotalLabel, int customerId, int shipperId) {
        if (orderTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Please add products to the order.");
            return;
        }
        try {
            // Calculate total amount
            String[] subtotalStrings = subtotalLabel.getText().split("\\$");
            String subtotalString = subtotalStrings[subtotalStrings.length - 1];
            Double totalAmount = Double.parseDouble(subtotalString);

            int orderId = orderService.createOrder(new Date(), customerId, shipperId, totalAmount);
            // Process each item in the order table
            for (int i = 0; i < orderTableModel.getRowCount(); i++) {
                int productId = (int) orderTableModel.getValueAt(i, 0);
                int quantity = (int) orderTableModel.getValueAt(i, 2);
                // Reduce stock quantity in product
                Product product = productService.getProductById(productId);
                product.setStockQuantity(product.getStockQuantity() - quantity);
                productService.updateProduct(product);
                OrderDetails orderDetail = new OrderDetails(orderId, productId, quantity);
                orderService.addOrderDetail(orderDetail);
            }
            JOptionPane.showMessageDialog(null, "Order created successfully!");
            // Clear the order table and reset the subtotal
            orderTableModel.setRowCount(0);
            updateSubtotal(orderTableModel, subtotalLabel);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating order: " + ex.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Order Details", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateSubtotal(DefaultTableModel orderTableModel, JLabel subtotalLabel) {
        double subtotal = 0.0;
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            subtotal += (double) orderTableModel.getValueAt(i, 4);
        }
        subtotalLabel.setText(String.format("Subtotal: $%.2f", subtotal));
    }

    public void fetchCustomerName(JTextField customerIdField, JLabel customerNameLabel) {
        String customerId = customerIdField.getText();
        if (!customerId.isEmpty()) {
            try {
                Integer.parseInt(customerId);
                String customerName = customerService.getCustomerById(Integer.parseInt(customerId)).getName();
                customerNameLabel.setText("Customer Name: " + customerName);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                customerNameLabel.setText("Customer Name: ");
            }
        } else {
            customerNameLabel.setText("Customer Name: ");
        }
    }

    public void addProductToOrder(JTextField productIdField, JTextField quantityField,
            DefaultTableModel orderTableModel, JLabel subtotalLabel) {
        try {
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            // Fetch product details from the service
            Product product = productService.getProductById(productId);
            String productName = product.getProductName();
            double price = product.getPrice();
            int stockQuantity = product.getStockQuantity();

            // Check if enough stock is available
            if (quantity > stockQuantity) {
                JOptionPane.showMessageDialog(null, "Not enough stock available for product: " + productName);
                return;
            }

            double total = Math.round(price * quantity * 100.0) / 100.0;
            // Add row to the table
            Object[] row = { productId, productName, quantity, price, total };
            orderTableModel.addRow(row);
            // Update the subtotal
            updateSubtotal(orderTableModel, subtotalLabel);
            // Clear input fields
            productIdField.setText("");
            quantityField.setText("");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter valid numbers for Product ID and Quantity.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error fetching product details: " + ex.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}