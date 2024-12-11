package com.retail.controller.Manager;

import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.retail.model.services.RedisService;

import redis.clients.jedis.Tuple;

public class BestSellingProductController {
    private RedisService redisService = new RedisService();

    public BestSellingProductController() {
    }

    public JTable createBestSellingProductTable() {
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product ID");
        model.addColumn("Sales");

        // Fetch data from Redis
        Set<Tuple> topProducts = redisService.getTopBestSellingProducts(15);
        for (Tuple product : topProducts) {
            model.addRow(new Object[] { product.getElement(), (int) product.getScore() });
        }

        table.setModel(model);

        return table;
    }

}
