package com.retail.view.Customer;

import com.retail.controller.Customer.ReviewController;
import com.retail.model.entities.Product;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReviewFrame extends JFrame {
    private ReviewController reviewController;

    public ReviewFrame(String customerPhone, Product product) {
        setTitle("Write a Review for " + product.getProductName());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel ratingLabel = new JLabel("Rating (1-5): ");
        JTextField ratingField = new JTextField();

        JLabel commentLabel = new JLabel("Comment: ");
        JTextArea commentArea = new JTextArea(3, 20);

        JButton submitButton = new JButton("Submit");
        reviewController = new ReviewController(ratingField, commentArea, customerPhone, this, product);
        submitButton.addActionListener(e -> reviewController.submitReview());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panel.add(ratingLabel);
        panel.add(ratingField);
        panel.add(commentLabel);
        panel.add(new JScrollPane(commentArea));
        panel.add(cancelButton);
        panel.add(submitButton);

        add(panel);

        setVisible(true);
    }
}
