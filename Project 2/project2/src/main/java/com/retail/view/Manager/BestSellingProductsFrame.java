package com.retail.view.Manager;

import javax.swing.*;
import com.retail.controller.BestSellingProductController;
import java.awt.*;

public class BestSellingProductsFrame extends JFrame {
    private BestSellingProductController controller = new BestSellingProductController();

    public BestSellingProductsFrame() {

        // Frame properties
        setTitle("Best-Selling Products");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTable table = controller.createBestSellingProductTable();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

}
