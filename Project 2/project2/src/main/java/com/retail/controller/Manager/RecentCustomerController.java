package com.retail.controller.Manager;

import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.retail.model.services.RedisService;

public class RecentCustomerController {
    private RedisService redisService = new RedisService();

    public JTable createRecentCustomersTable() {
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

        return table;
    }

}
