package com.retail.controller.Customer;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.retail.model.dao.ReviewDAO;
import com.retail.model.entities.Product;
import com.retail.model.entities.Review;
import com.retail.model.services.ReviewService;
import com.retail.view.Customer.ReviewFrame;

public class ReviewController {
    private ReviewService reviewService = new ReviewService(new ReviewDAO());
    private JTextField ratingField;
    private JTextArea commentArea;
    private String customerPhone;
    private ReviewFrame reviewFrame;
    private Product product;

    public ReviewController(JTextField ratingField, JTextArea commentArea, String customerPhone,
            ReviewFrame reviewFrame, Product product) {
        this.ratingField = ratingField;
        this.commentArea = commentArea;
        this.customerPhone = customerPhone;
        this.reviewFrame = reviewFrame;
        this.product = product;
    }

    public void submitReview() {
        try {
            int rating = Integer.parseInt(ratingField.getText());
            String comment = commentArea.getText();

            if (rating < 1 || rating > 5) {
                JOptionPane.showMessageDialog(reviewFrame, "Rating must be between 1 and 5.");
                return;
            }

            Review review = new Review(product.getProductId(), customerPhone, rating, comment);
            reviewService.addReview(review);

            JOptionPane.showMessageDialog(reviewFrame, "Review submitted successfully!");
            reviewFrame.dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(reviewFrame, "Please enter a valid rating.");
        }
    }

}
