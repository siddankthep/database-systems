package com.retail.view.Employee;

import com.retail.controller.OrderController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
    private JLabel customerNameLabel;
    private OrderController orderController;
    private int shipperId;

    public CreateOrderFrame() {
        this.orderController = new OrderController();
        setTitle("Create Order");
        setSize(1100, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Input Panel for Product ID and Quantity
        JPanel inputPanel = new JPanel();
        GroupLayout layout = new GroupLayout(inputPanel);
        inputPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel productIdLabel = new JLabel("Product ID:");
        productIdField = new JTextField();
        productIdField.setPreferredSize(new Dimension(50, 25));

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(50, 25));

        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdField = new JTextField();
        customerIdField.setPreferredSize(new Dimension(50, 25));

        customerIdField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                orderController.fetchCustomerName(customerIdField, customerNameLabel);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                orderController.fetchCustomerName(customerIdField, customerNameLabel);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                orderController.fetchCustomerName(customerIdField, customerNameLabel);
            }
        });

        JButton addButton = new JButton("Add to Order");
        addButton.addActionListener(
                e -> orderController.addProductToOrder(productIdField, quantityField, orderTableModel, subtotalLabel));

        JButton removeButton = new JButton("Remove from Order");
        removeButton.addActionListener(
                e -> orderController.removeItemFromOrder(orderTable, orderTableModel, subtotalLabel));

        shipOrderCheckbox = new JCheckBox("Ship this order");
        shipOrderCheckbox.addActionListener(e -> {
            if (shipOrderCheckbox.isSelected()) {
                shipperId = orderController.assignShipper(assignedShipperLabel);
            } else {
                shipperId = 1;
                assignedShipperLabel.setText("Assigned Shipper: None");
            }
        });

        createOrderButton = new JButton("Create Order");
        createOrderButton.addActionListener(e -> {
            int customerId = Integer.parseInt(customerIdField.getText());
            orderController.createOrder(orderTableModel, subtotalLabel, customerId, shipperId);
        });

        // Set horizontal group
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(productIdLabel)
                        .addComponent(productIdField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(quantityLabel)
                        .addComponent(quantityField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(customerIdLabel)
                        .addComponent(customerIdField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(addButton)
                        .addComponent(removeButton)
                        .addComponent(createOrderButton)
                        .addComponent(shipOrderCheckbox));

        // Set vertical group
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(productIdLabel)
                        .addComponent(productIdField)
                        .addComponent(quantityLabel)
                        .addComponent(quantityField)
                        .addComponent(customerIdLabel)
                        .addComponent(customerIdField)
                        .addComponent(addButton)
                        .addComponent(removeButton)
                        .addComponent(createOrderButton)
                        .addComponent(shipOrderCheckbox));

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

        customerNameLabel = new JLabel("Customer Name: ");

        subtotalPanel.add(customerNameLabel);
        subtotalPanel.add(new JLabel("             |             "));
        subtotalPanel.add(assignedShipperLabel);
        subtotalPanel.add(new JLabel("             |             "));
        subtotalLabel = new JLabel("Subtotal: $0.00");
        subtotalPanel.add(subtotalLabel);

        add(subtotalPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}