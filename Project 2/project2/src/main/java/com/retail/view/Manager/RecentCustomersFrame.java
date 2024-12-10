package com.retail.view.Manager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.retail.model.services.RedisService;

import redis.clients.jedis.Jedis;

import java.awt.*;
import java.util.Set;

public class RecentCustomersFrame extends JFrame {
    RedisService redisService = new RedisService();

    public RecentCustomersFrame() {

        // Frame properties
        setTitle("Recent Customer Purchases");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a table to display recent customer purchases
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Customer Phone");
        model.addColumn("Purchase Date");

        // Fetch recent customer purchases from Redis
        Set<String> recentPurchases = redisService.getRecentCustomerPurchases(10);
        if (recentPurchases.isEmpty()) {
            model.addRow(new Object[] { "No data", "No data" });
        } else {
            for (String purchase : recentPurchases) {
                // Split the stored string into phone and purchase date
                String[] parts = purchase.split(":");
                String phone = parts[0];
                String purchaseDate = parts[1];

                // Add row to the table
                model.addRow(new Object[] { phone, purchaseDate });
            }
        }

        table.setModel(model);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

}
