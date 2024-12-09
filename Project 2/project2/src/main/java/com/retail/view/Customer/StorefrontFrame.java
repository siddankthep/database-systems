package com.retail.view.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.retail.model.dao.CustomerDAO;
import com.retail.model.dao.ProductDAO;
import com.retail.model.entities.CustomerMongo;
import com.retail.model.entities.OrderDetails;
import com.retail.model.entities.Product;
import com.retail.model.services.CustomerService;
import com.retail.model.services.ProductService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableCellRenderer;

public class StorefrontFrame extends JFrame {
    private ProductService productService = new ProductService(new ProductDAO());
    private CustomerService customerService = new CustomerService(new CustomerDAO());
    private JTable productTable;
    private DefaultTableModel tableModel;
    private List<OrderDetails> cart = new ArrayList<>();
    private List<Product> products;
    private JButton viewCartButton;
    private JButton goToPaymentButton;
    private CustomerMongo customer;

    public StorefrontFrame(String phone) {
        this.customer = customerService.findCustomerByPhoneMongo(phone);
        setTitle("Storefront");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Product Table
        String[] columnNames = { "Product ID", "Name", "Price", "Quantity", "Increase", "Decrease" };
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        productTable.setRowHeight(30); // Set row height to 30 pixels

        // Load products into the table
        try {
            products = productService.getAllProducts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
            e.printStackTrace();
        }
        for (Product product : products) {
            tableModel.addRow(new Object[] {
                    product.getProductId(),
                    product.getProductName(),
                    product.getPrice(),
                    0, // Initial quantity
                    "➕", // Increase button
                    "➖" // Decrease button
            });
        }

        // Add button renderers for the "Increase" and "Decrease" columns
        productTable.getColumn("Increase").setCellRenderer(new ButtonRenderer());
        productTable.getColumn("Decrease").setCellRenderer(new ButtonRenderer());

        // Add button editors for the "Increase" and "Decrease" columns
        productTable.getColumn("Increase").setCellEditor(new ButtonEditor(new JButton("➕"), true));
        productTable.getColumn("Decrease").setCellEditor(new ButtonEditor(new JButton("➖"), false));

        // Add the product table to the frame
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        add(buttonPanel, BorderLayout.SOUTH);

        // View Cart Button
        viewCartButton = new JButton("Add to Cart");
        viewCartButton.addActionListener(e -> addToCart());
        buttonPanel.add(viewCartButton);

        goToPaymentButton = new JButton("Go to Payment [0]");
        goToPaymentButton.addActionListener(e -> goToPay());
        buttonPanel.add(goToPaymentButton);

        setVisible(true);
    }

    // Show cart details
    private void addToCart() {

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int quantity = (int) tableModel.getValueAt(i, 3); // Quantity column
            if (quantity > 0) {
                Product product = products.get(i);
                OrderDetails orderDetails = new OrderDetails(product.getProductId(), quantity);
                cart.add(orderDetails);
            }
        }
        int productCount = cart.size();
        if (productCount > 0) {
            JOptionPane.showMessageDialog(this, "Added " + productCount + " products to cart");
        } else {
            JOptionPane.showMessageDialog(this, "No products added to cart");
        }
        goToPaymentButton.setText("Go to Payment [" + productCount + "]");

    }

    private void goToPay() {
        PaymentFrame paymentFrame = new PaymentFrame(cart, customer, this);
        paymentFrame.setVisible(true);
        this.setVisible(false);
    }

    public void clearQuantityAndCart() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(0, i, 3); // Quantity column
        }
        cart.clear();
        goToPaymentButton.setText("Go to Payment [0]");
    }

    // Button renderer for JTable
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText(value.toString());
            return this;
        }
    }

    // Button editor for JTable
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isIncrease;

        public ButtonEditor(JButton button, boolean isIncrease) {
            super(new JTextField());
            this.button = button;
            this.isIncrease = isIncrease;

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = productTable.getSelectedRow();
                    int currentQuantity = (int) tableModel.getValueAt(row, 3); // Quantity column
                    if (isIncrease) {
                        tableModel.setValueAt(currentQuantity + 1, row, 3); // Increase quantity
                    } else {
                        if (currentQuantity > 0) {
                            tableModel.setValueAt(currentQuantity - 1, row, 3); // Decrease quantity
                        }
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            button.setText(value.toString());
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }
    }
}
