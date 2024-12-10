package com.retail.view.Customer;

import com.retail.model.dao.ReviewDAO;
import com.retail.model.entities.Product;
import com.retail.model.entities.Review;
import com.retail.model.services.ReviewService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReviewFrame extends JFrame {
    private ReviewService reviewService = new ReviewService(new ReviewDAO());

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
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int rating = Integer.parseInt(ratingField.getText());
                    String comment = commentArea.getText();

                    if (rating < 1 || rating > 5) {
                        JOptionPane.showMessageDialog(ReviewFrame.this, "Rating must be between 1 and 5.");
                        return;
                    }

                    Review review = new Review(product.getProductId(), customerPhone, rating, comment);
                    reviewService.addReview(review);

                    JOptionPane.showMessageDialog(ReviewFrame.this, "Review submitted successfully!");
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ReviewFrame.this, "Please enter a valid rating.");
                }
            }
        });

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
