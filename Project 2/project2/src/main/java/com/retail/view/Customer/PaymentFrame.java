package com.retail.view.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.retail.controller.PaymentController;
import com.retail.model.entities.CustomerMongo;
import com.retail.model.entities.OrderDetails;

public class PaymentFrame extends JFrame {
    private PaymentController paymentController;
    private double subtotal;

    public PaymentFrame(List<OrderDetails> cart, CustomerMongo customer, StorefrontFrame storefront) {
        this.paymentController = new PaymentController(cart, customer, storefront, this, subtotal);

        setTitle("Payment");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the layout
        setLayout(new BorderLayout());

        // Add the product table
        add(paymentController.createProductTable(), BorderLayout.CENTER);

        // Add the subtotal field
        add(paymentController.createControlPanel(), BorderLayout.SOUTH);
    }

}
