package com.retail.view.Manager;

import javax.swing.*;

import com.retail.controller.Manager.CheckReviewsController;

import java.awt.*;

public class CheckReviewsFrame extends JFrame {
    private CheckReviewsController controller = new CheckReviewsController();
    private JTable reviewsTable;

    public CheckReviewsFrame() {
        setTitle("Past Reviews");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the table to display reviews
        reviewsTable = controller.createReviewsTable();

        // Add components to the frame
        add(new JScrollPane(reviewsTable), BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("All Past Reviews", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        setVisible(true);
    }

}
