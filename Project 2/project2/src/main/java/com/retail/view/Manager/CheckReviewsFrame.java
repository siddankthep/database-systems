package com.retail.view.Manager;

import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.ReviewDAO;
import com.retail.model.entities.Review;
import com.retail.model.services.ProductService;
import com.retail.model.services.ReviewService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CheckReviewsFrame extends JFrame {
    private ReviewService reviewService = new ReviewService(new ReviewDAO());
    private ProductService productService = new ProductService(new ProductDAO());
    private JTable reviewsTable;
    private DefaultTableModel tableModel;

    public CheckReviewsFrame() {
        setTitle("Past Reviews");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fetch reviews from the repository
        List<Review> reviews = reviewService.getAllReviews();

        // Create the table to display reviews
        reviewsTable = createReviewsTable(reviews);

        // Add components to the frame
        add(new JScrollPane(reviewsTable), BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("All Past Reviews", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        setVisible(true);
    }

    private JTable createReviewsTable(List<Review> reviews) {
        String[] columnNames = { "Product ID", "Product Name", "Customer Phone", "Rating", "Comment" };
        tableModel = new DefaultTableModel(columnNames, 0);

        try {

            for (Review review : reviews) {
                int productId = review.getProductId();
                tableModel.addRow(new Object[] {
                        productId,
                        productService.getProductById(productId).getProductName(),
                        review.getCustomerPhone(),
                        review.getRating(),
                        review.getComment()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error creating reviews table: " + e.getMessage());
        }
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setEnabled(false); // Disable editing

        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);

        return table;
    }

}
