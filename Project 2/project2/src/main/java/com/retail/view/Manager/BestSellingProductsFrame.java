package com.retail.view.Manager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.retail.model.services.RedisService;

import redis.clients.jedis.Tuple;
import java.awt.*;
import java.util.Set;

public class BestSellingProductsFrame extends JFrame {

    private RedisService redisService = new RedisService();

    public BestSellingProductsFrame() {

        // Frame properties
        setTitle("Best-Selling Products");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a table to display the best-selling products
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product ID");
        model.addColumn("Sales");

        // Fetch data from Redis
        Set<Tuple> topProducts = redisService.getTopBestSellingProducts(15);
        for (Tuple product : topProducts) {
            model.addRow(new Object[]{product.getElement(), (int) product.getScore()});
        }

        table.setModel(model);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

}
