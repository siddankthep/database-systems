package com.retail.controller;

import java.util.List;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.retail.model.dao.OrderDAO;
import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.ShipperDAO;
import com.retail.model.entities.CustomerMongo;
import com.retail.model.entities.OrderDetails;
import com.retail.model.entities.OrderMongo;
import com.retail.model.entities.Product;
import com.retail.model.services.OrderService;
import com.retail.model.services.ProductService;
import com.retail.model.services.RedisService;
import com.retail.model.services.ShipperService;
import com.retail.view.Customer.PaymentFrame;
import com.retail.view.Customer.StorefrontFrame;

public class PaymentController {
    private ProductService productService = new ProductService(new ProductDAO());
    private RedisService redisService = new RedisService();
    private ShipperService shipperService = new ShipperService(new ShipperDAO());
    private OrderService orderService = new OrderService(new OrderDAO());
    private double subtotal;
    private List<OrderDetails> cart;
    private CustomerMongo customer;
    private StorefrontFrame storefront;
    private PaymentFrame payment;

    public PaymentController(List<OrderDetails> cart, CustomerMongo customer, StorefrontFrame storefront,
            PaymentFrame payment, double subtotal) {
        this.subtotal = subtotal;
        this.cart = cart;
        this.customer = customer;
        this.storefront = storefront;
        this.payment = payment;
    }

    public JPanel createControlPanel() {

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton backButton = new JButton("Back to Cart");
        backButton.addActionListener(e -> {
            payment.dispose();
            storefront.setVisible(true);
        });
        buttonPanel.add(backButton);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> {
            try {
                for (OrderDetails details : cart) {
                    int productId = details.getProductId();
                    Product product = productService.getProductById(productId);
                    int quantity = details.getQuantity();
                    productService.updateProductItemsSold(product, quantity);
                    redisService.incrementProductSales(product.getProductName(), quantity);
                    redisService.addRecentCustomer(customer.getPhone(), new Date());
                }
                OrderMongo order = new OrderMongo(new Date(), shipperService.getRandomShipperId(), this.subtotal,
                        "Paid",
                        cart, customer.getPhone());
                orderService.createOrderMongo(order);
                JOptionPane.showMessageDialog(payment, "Order created successfully!");
                payment.dispose();
                storefront.getController().clearQuantityAndCart();
                storefront.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(payment, "Error creating order: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        buttonPanel.add(checkoutButton);

        JButton payAtCashierButton = new JButton("Pay at Cashier");
        payAtCashierButton.addActionListener(e -> {
            try {
                for (OrderDetails details : cart) {
                    int productId = details.getProductId();
                    Product product = productService.getProductById(productId);
                    int quantitySold = details.getQuantity();
                    int currentStock = product.getStockQuantity();
                    int currentItemsSold = product.getItemsSold();
                    if (quantitySold > currentStock) {
                        JOptionPane.showMessageDialog(payment, "Not enough stock, only " + currentStock + " left.");
                        return;
                    }
                    product.setStockQuantity(currentStock - quantitySold);
                    product.setItemsSold(currentItemsSold + quantitySold);

                    productService.updateProduct(product);
                    redisService.incrementProductSales(product.getProductName(), quantitySold);
                    redisService.addRecentCustomer(customer.getPhone(), new Date());
                }
                OrderMongo order = new OrderMongo(new Date(), shipperService.getRandomShipperId(), this.subtotal,
                        "Unpaid",
                        cart, customer.getPhone());
                orderService.createOrderMongo(order);
                JOptionPane.showMessageDialog(payment, "Order created successfully! Please pay at the cashier.");
                payment.dispose();
                storefront.getController().clearQuantityAndCart();
                storefront.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(payment, "Error creating order: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        buttonPanel.add(payAtCashierButton);

        JPanel subTotalPanel = new JPanel();
        subTotalPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Calculate subtotal
        subtotal = cart.stream().mapToDouble(details -> {
            try {
                Product product = productService.getProductById(details.getProductId());
                return product.getPrice() * details.getQuantity();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(payment, "Error loading product: " + e.getMessage());
                e.printStackTrace();
                return 0.0;
            }
        }).sum();

        // Round subtotal to 2 decimal points
        BigDecimal roundedSubtotal = new BigDecimal(subtotal).setScale(2, RoundingMode.HALF_UP);
        subtotal = roundedSubtotal.doubleValue();

        // Add subtotal label
        JLabel subtotalLabel = new JLabel("Subtotal: $" + String.format("%.2f", subtotal));
        subtotalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        subTotalPanel.add(subtotalLabel);

        controlPanel.add(buttonPanel, BorderLayout.WEST);
        controlPanel.add(subTotalPanel, BorderLayout.EAST);

        return controlPanel;
    }

    public JScrollPane createProductTable() {
        // Define table columns
        String[] columnNames = { "Product Name", "Price", "Quantity", "Total" };

        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);

        // Populate the table with cart data
        for (OrderDetails details : cart) {
            int productId = details.getProductId();
            try {
                Product product = productService.getProductById(productId);
                double price = product.getPrice();
                int quantity = details.getQuantity();
                tableModel.addRow(new Object[] {
                        product.getProductName(),
                        price,
                        quantity,
                        price * quantity
                });
            } catch (Exception e) {
                JOptionPane.showMessageDialog(payment, "Error loading product: " + e.getMessage());
                e.printStackTrace();
            }

        }

        // Disable editing in the table
        table.setEnabled(false);

        // Return the table wrapped in a scroll pane
        return new JScrollPane(table);
    }

}
