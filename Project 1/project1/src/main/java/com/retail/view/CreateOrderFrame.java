package com.retail.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.antlr.v4.runtime.misc.IntegerStack;

import com.retail.model.services.OrderService;
import com.retail.model.services.ProductService;
import com.retail.model.services.ShipperService;
import com.retail.model.entities.OrderDetails;
import com.retail.model.entities.Shipper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

public class CreateOrderFrame extends JFrame {
    private JTextField productIdField;
    private JTextField quantityField;
    private JTextField customerIdField;
    private JTable orderTable;
    private DefaultTableModel orderTableModel;
    private JLabel subtotalLabel;
    private JCheckBox shipOrderCheckbox;
    private JButton createOrderButton;
    private JLabel assignedShipperLabel;
    private OrderService orderService;
    private ProductService productService;
    private ShipperService shipperService;
    private int shipperId;

    public CreateOrderFrame(OrderService orderService, ProductService productService, ShipperService shipperService) {
        this.productService = productService;
        this.orderService = orderService;
        this.shipperService = shipperService;
        setTitle("Create Order");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Input Panel for Product ID and Quantity
        JPanel inputPanel = new JPanel(new GridLayout(2, 5));

        inputPanel.add(new JLabel("Product ID:"));
        productIdField = new JTextField();
        inputPanel.add(productIdField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        inputPanel.add(customerIdField);

        JButton addButton = new JButton("Add to Order");
        addButton.addActionListener(new AddProductButtonListener());
        inputPanel.add(addButton);

        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel(""));

        shipOrderCheckbox = new JCheckBox("Ship this order");
        createOrderButton = new JButton("Create Order");
        createOrderButton.addActionListener(new CreateOrderButtonListener());

        inputPanel.add(shipOrderCheckbox);
        inputPanel.add(createOrderButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table to display items in the order
        String[] columnNames = { "Product ID", "Product Name", "Quantity", "Price", "Total" };
        orderTableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(orderTableModel);
        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Subtotal Panel
        JPanel subtotalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        assignedShipperLabel = new JLabel("Assigned Shipper: None");

        // Add listener to checkbox to assign shipper if selected
        shipOrderCheckbox.addActionListener(e -> {
            if (shipOrderCheckbox.isSelected()) {
                shipperId = assignShipper();
            } else {
                shipperId = 3;
                assignedShipperLabel.setText("Assigned Shipper: None");
            }
        });

        subtotalPanel.add(assignedShipperLabel);
        subtotalPanel.add(new JLabel("             |             "));
        subtotalLabel = new JLabel("Subtotal: $0.00");
        subtotalPanel.add(subtotalLabel);
        add(subtotalPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    class CreateOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (orderTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(CreateOrderFrame.this, "Please add products to the order.");
                return;
            }

            // Create an order and add order details
            try {
                // Create Order object
                Integer customerId = Integer.parseInt(customerIdField.getText());
                // Integer shipperId = shipperId; // shipperID

                // Calculate total amount
                String[] subtotalStrings = subtotalLabel.getText().split("\\$");
                String subtotalString = subtotalStrings[subtotalStrings.length - 1];
                Double totalAmount = Double.parseDouble(subtotalString);

                int orderId = orderService.createOrder(new Date(), customerId, shipperId, totalAmount, "Pending");

                // Process each item in the order table
                for (int i = 0; i < orderTableModel.getRowCount(); i++) {
                    int productId = (int) orderTableModel.getValueAt(i, 0);
                    int quantity = (int) orderTableModel.getValueAt(i, 2);
                    OrderDetails orderDetail = new OrderDetails(orderId, productId, quantity);
                    orderService.addOrderDetail(orderDetail);
                }

                JOptionPane.showMessageDialog(CreateOrderFrame.this, "Order created successfully!");

                // Clear the order table and reset the subtotal
                orderTableModel.setRowCount(0);
                updateSubtotal();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(CreateOrderFrame.this, "Error creating order: "
                        + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(CreateOrderFrame.this, ex.getMessage(), "Invalid Order Details",
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

                // Fetch product details from the service
                String productName = productService.getProductNameById(productId); // This should return the product
                                                                                   // name
                double price = productService.getProductPriceById(productId); // This should return the product price

                double total = price * quantity;

                // Add row to the table
                Object[] row = { productId, productName, quantity, price, total };
                orderTableModel.addRow(row);

                // Update the subtotal
                updateSubtotal();

                // Clear input fields
                productIdField.setText("");
                quantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CreateOrderFrame.this,
                        "Please enter valid numbers for Product ID and Quantity.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(CreateOrderFrame.this,
                        "Error fetching product details: " + ex.getMessage(), "Database Error",
                        JOptionPane.ERROR_MESSAGE);
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

    private int assignShipper() {

        try {

            int shipperId = shipperService.getRandomShipperId();
            Shipper shipper = shipperService.getShipperById(shipperId);
            String shipperName = shipper.getShipperName();
            String assignedShipper = "Auto-Assigned Shipper: " + shipperName;
            assignedShipperLabel.setText(assignedShipper);
            return shipperId;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(CreateOrderFrame.this, "Error assigning shipper: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            return 3;
        }
    }
}
