package com.retail.controller.Manager;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.ReviewDAO;
import com.retail.model.entities.Review;
import com.retail.model.services.ProductService;
import com.retail.model.services.ReviewService;

public class CheckReviewsController {
    private ProductService productService = new ProductService(new ProductDAO());
    private ReviewService reviewService = new ReviewService(new ReviewDAO());

    public JTable createReviewsTable() {
        String[] columnNames = { "Product ID", "Product Name", "Customer Phone", "Rating", "Comment" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        List<Review> reviews = reviewService.getAllReviews();

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
