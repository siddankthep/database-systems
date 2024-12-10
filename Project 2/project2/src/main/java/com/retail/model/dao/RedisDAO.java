package com.retail.model.dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;

import com.retail.utils.RedisDBConnection;

public class RedisDAO {

    private Jedis jedis = RedisDBConnection.getInstance().getJedis();

    // Constructor to initialize the Redis connection
    public RedisDAO() {
    }

    // Method to increment product sales
    public void incrementProductSales(int productId, double quantity) {
        String productIdString = String.valueOf(productId);
        jedis.zincrby("product:sales", quantity, productIdString);
    }

    // Method to retrieve top N best-selling products
    public Set<Tuple> getTopBestSellingProducts(int topN) {
        return jedis.zrevrangeWithScores("product:sales", 0, topN - 1);
    }

    // Method to add a recent customer
    public void addRecentCustomer(String customerPhone) {
        jedis.lpush("customer:recent", customerPhone);
        jedis.ltrim("customer:recent", 0, 99); // Limit the list to the most recent 100 customers
    }

    // Method to retrieve the most recent N customers
    public List<String> getRecentCustomers(int limit) {
        return jedis.lrange("customer:recent", 0, limit - 1);
    }

    // Method to add or update a customer's details
    public void setCustomerDetails(String customerPhone, String field, String value) {
        jedis.hset("customer:" + customerPhone, field, value);
    }

    // Method to retrieve a customer's details
    public String getCustomerDetail(String customerPhone, String field) {
        return jedis.hget("customer:" + customerPhone, field);
    }

    // Method to get all details for a customer
    public Set<String> getAllCustomerFields(String customerPhone) {
        return jedis.hkeys("customer:" + customerPhone);
    }


    // Close the Redis connection
    public void close() {
        if (jedis != null) {
            jedis.close();
        }
    }

    public static void main(String[] args) {
        RedisDAO redisDAO = new RedisDAO();

        // Increment product sales
        redisDAO.incrementProductSales(1, 5);
        redisDAO.incrementProductSales(2, 10);
        redisDAO.incrementProductSales(3, 15);

        // Retrieve top 3 best-selling products
        Set<Tuple> bestSellingProducts = redisDAO.getTopBestSellingProducts(3);
        System.out.println("Top 3 Best-Selling Products:");
        for (Tuple product : bestSellingProducts) {
            System.out.println("Product ID: " + product.getElement() + ", Sales: " + product.getScore());
        }

        // Add recent customers
        redisDAO.addRecentCustomer("1234");
        redisDAO.addRecentCustomer("1235");
        redisDAO.addRecentCustomer("1236");

        // Retrieve the most recent 2 customers
        List<String> recentCustomers = redisDAO.getRecentCustomers(2);
        System.out.println("Most Recent Customers:");
        for (String customer : recentCustomers) {
            System.out.println("Customer ID: " + customer);
        }

        // Set customer details
        redisDAO.setCustomerDetails("1234", "name", "Alice");
        redisDAO.setCustomerDetails("1234", "email", "alice@gmail.com");
        
    }
}
