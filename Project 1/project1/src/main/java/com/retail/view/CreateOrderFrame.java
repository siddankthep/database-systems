package com.retail.view;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.retail.model.services.CustomerService;
import com.retail.model.services.OrderService;
import com.retail.model.services.ProductService;
import com.retail.model.services.ShipperService;
import com.retail.model.dao.CustomerDAO;
import com.retail.model.dao.OrderDAO;
import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.ShipperDAO;
import com.retail.model.entities.Customer;
import com.retail.model.entities.OrderDetails;
import com.retail.model.entities.Product;
import com.retail.model.entities.Shipper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    private JLabel customerNameLabel;
    private OrderService orderService;
    private ProductService productService;
    private ShipperService shipperService;
    private CustomerService customerService;
    private int shipperId = 1; // Default null shipper ID

    public CreateOrderFrame(OrderService orderService, ProductService productService, ShipperService shipperService,
            CustomerService customerService) {
        this.customerService = customerService;
        this.productService = productService;
        this.orderService = orderService;
        this.shipperService = shipperService;
        setTitle("Create Order");
        setSize(900, 400);
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
                fetchCustomerName();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fetchCustomerName();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fetchCustomerName();
            }

        });

        JButton addButton = new JButton("Add to Order");
        addButton.addActionListener(new AddProductButtonListener());

        shipOrderCheckbox = new JCheckBox("Ship this order");
        createOrderButton = new JButton("Create Order");
        createOrderButton.addActionListener(new CreateOrderButtonListener());

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

        // Add listener to checkbox to assign shipper if selected
        shipOrderCheckbox.addActionListener(e -> {
            if (shipOrderCheckbox.isSelected()) {
                shipperId = assignShipper();
            } else {
                shipperId = 1;
                assignedShipperLabel.setText("Assigned Shipper: None");
            }
        });

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

    private void fetchCustomerName() {
        String customerId = customerIdField.getText();
        if (!customerId.isEmpty()) {
            // Replace this with actual logic to fetch customer name from database or
            // service
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

                System.out.println("Shipper ID: " + shipperId);
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

                JOptionPane.showMessageDialog(CreateOrderFrame.this, "Order created successfully!");

                // Clear the order table and reset the subtotal
                orderTableModel.setRowCount(0);
                updateSubtotal();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(CreateOrderFrame.this, "Error creating order: "
                        + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(CreateOrderFrame.this, ex.getMessage(), "Invalid Order Details",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class AddProductButtonListener implements ActionListener { // TODO: check if product out of stock
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int productId = Integer.parseInt(productIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                // Fetch product details from the service
                Product product = productService.getProductById(productId);
                String productName = product.getProductName();
                double price = product.getPrice();
                int stockQuantity = product.getStockQuantity();

                if (quantity > stockQuantity) {
                    JOptionPane.showMessageDialog(CreateOrderFrame.this,
                            "Not enough stock available for product: " + productName);
                    return;
                }

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
            return 1;
        }
    }

    // public static void main(String[] args) {
    //     new CreateOrderFrame(new OrderService(new OrderDAO()), new ProductService(new ProductDAO()),
    //             new ShipperService(new ShipperDAO()), new CustomerService(new CustomerDAO()));
    // }
}
