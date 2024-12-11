package com.retail.controller.Customer;

import java.awt.Component;
import java.awt.event.*;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.retail.model.dao.ProductDAO;
import com.retail.model.entities.CustomerMongo;
import com.retail.model.entities.OrderDetails;
import com.retail.model.entities.Product;
import com.retail.model.services.ProductService;
import com.retail.view.Customer.PaymentFrame;
import com.retail.view.Customer.StorefrontFrame;

public class StorefrontController {
    private ProductService productService = new ProductService(new ProductDAO());
    private JTable productTable;
    private DefaultTableModel tableModel;
    private List<OrderDetails> cart;
    private List<Product> products;
    private StorefrontFrame storefront;
    private CustomerMongo customer;
    private JButton goToPaymentButton;

    public StorefrontController(StorefrontFrame storefront, CustomerMongo customer, List<OrderDetails> cart,
            JButton goToPaymentButton) {
        this.storefront = storefront;
        this.customer = customer;
        this.cart = cart;
        this.goToPaymentButton = goToPaymentButton;
    }

    public JTable createProductTable() {
        // Product Table
        String[] columnNames = { "Product ID", "Name", "Price", "Quantity", "Increase", "Decrease" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 3;
            }
        };
        productTable = new JTable(tableModel);
        productTable.setRowHeight(30); // Set row height to 30 pixels

        // Load products into the table
        try {
            products = productService.getAllProducts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(storefront, "Error loading products: " + e.getMessage());
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

        return productTable;
    }

    // Show cart details
    public void addToCart() {

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
            JOptionPane.showMessageDialog(storefront, "Added " + productCount + " products to cart");
        } else {
            JOptionPane.showMessageDialog(storefront, "No products added to cart");
        }
        goToPaymentButton.setText("Go to Payment [" + productCount + "]");

    }

    public void goToPay() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(storefront, "Please add products to the cart first");
            return;
        }
        PaymentFrame paymentFrame = new PaymentFrame(cart, customer, storefront);
        paymentFrame.setVisible(true);
        storefront.setVisible(false);
    }

    public void clearQuantityAndCart() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(0, i, 3); // Quantity column
        }
        cart.clear();
        goToPaymentButton.setText("Go to Payment [0]");
    }

    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;

        public ButtonEditor(JButton button, boolean isIncrease) {
            super(new JTextField());
            this.button = button;

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
}
