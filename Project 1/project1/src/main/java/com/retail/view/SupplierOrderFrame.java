package com.retail.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.retail.model.services.ProductService;
import com.retail.model.services.SupplierOrderService;
import com.retail.model.services.SupplierService;
import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.SupplierDAO;
import com.retail.model.dao.SupplierOrderDAO;
import com.retail.model.entities.Product;
import com.retail.model.entities.Supplier;
import com.retail.model.entities.SupplierOrderDetails;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

public class SupplierOrderFrame extends JFrame {
    private JTextField productIdField;
    private JTextField quantityField;
    private JTable orderTable;
    private DefaultTableModel orderTableModel;
    private JLabel subtotalLabel;
    private JButton createOrderButton;
    private SupplierOrderService supplierOrderService;
    private ProductService productService;
    private SupplierService supplierService;
    private int supplierId = -1;
    // private double totalAmount = 0.0;

    public SupplierOrderFrame(SupplierOrderService supplierOrderService, ProductService productService,
            SupplierService supplierService) {
        this.productService = productService;
        this.supplierOrderService = supplierOrderService;
        this.supplierService = supplierService;
        setTitle("Create Supplier Order");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // setLocationRelativeTo(null);
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
        addButton.addActionListener(new AddProductButtonListener());
        createOrderButton = new JButton("Create Order");
        createOrderButton.addActionListener(new CreateSupplierOrderButtonListener());
        // createOrderButton.setPreferredSize(new Dimension(200, 25));

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

    class CreateSupplierOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (orderTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(SupplierOrderFrame.this, "Please add products to the order.");
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

                JOptionPane.showMessageDialog(SupplierOrderFrame.this, "Order created successfully!");

                // Clear the order table and reset the subtotal
                orderTableModel.setRowCount(0);
                totalAmount = 0.0;
                updateSubtotal();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(SupplierOrderFrame.this, "Error creating order: "
                        + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(SupplierOrderFrame.this, ex.getMessage(), "Invalid Order Details",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class AddProductButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int productId = Integer.parseInt(productIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                Product product = productService.getProductById(productId);
                if (product == null) {
                    JOptionPane.showMessageDialog(SupplierOrderFrame.this, "Product not found.");
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
                    JOptionPane.showMessageDialog(SupplierOrderFrame.this,
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
                updateSubtotal(); // TODO: Check if this works

                // Clear input fields
                productIdField.setText("");
                quantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(SupplierOrderFrame.this,
                        "Please enter valid numbers for Product ID and Quantity.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(SupplierOrderFrame.this,
                        "Error fetching product details: " + ex.getMessage(), "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateSubtotal() {
        double subtotal = 0.0;
        for (int i = 0; i < orderTableModel.getRowCount(); i++) {
            subtotal += (double) orderTableModel.getValueAt(i, 5);
        }
        subtotalLabel.setText(String.format("Subtotal: $%.2f", subtotal));
    }

    // public static void main(String[] args) {
    // new SupplierOrderFrame(new SupplierOrderService(new SupplierOrderDAO()), new
    // ProductService(new ProductDAO()),
    // new SupplierService(new SupplierDAO()));
    // }
}
