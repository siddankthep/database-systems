package com.retail.view.Customer;

import javax.swing.*;
import com.retail.controller.StorefrontController;
import com.retail.model.dao.CustomerDAO;
import com.retail.model.entities.CustomerMongo;
import com.retail.model.entities.OrderDetails;
import com.retail.model.services.CustomerService;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StorefrontFrame extends JFrame {
    private CustomerService customerService = new CustomerService(new CustomerDAO());
    private StorefrontController controller;
    private JTable productTable;
    private List<OrderDetails> cart = new ArrayList<>();
    private JButton viewCartButton = new JButton("Add to Cart");
    private JButton goToPaymentButton = new JButton("Go to Payment [0]");
    private CustomerMongo customer;

    public StorefrontFrame(String phone) {
        this.customer = customerService.findCustomerByPhoneMongo(phone);
        controller = new StorefrontController(this, customer, cart, goToPaymentButton);
        setTitle("Storefront");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        productTable = controller.createProductTable();

        // Add the product table to the frame
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        add(buttonPanel, BorderLayout.SOUTH);

        // View Cart Button
        viewCartButton.addActionListener(e -> controller.addToCart());
        goToPaymentButton.addActionListener(e -> controller.goToPay());
        
        buttonPanel.add(viewCartButton);
        buttonPanel.add(goToPaymentButton);

        setVisible(true);
    }

    public StorefrontController getController() {
        return controller;
    }
}
