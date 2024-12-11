package com.retail.model.dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Date;
import java.util.Set;

import com.retail.utils.RedisDBConnection;

public class RedisDAO {

    private Jedis jedis = RedisDBConnection.getInstance().getJedis();

    // Constructor to initialize the Redis connection
    public RedisDAO() {
    }

    // Method to increment product sales
    public void incrementProductSales(String productName, double quantity) {
        jedis.zincrby("product:sales", quantity, productName);
    }

    // Method to retrieve top N best-selling products
    public Set<Tuple> getTopBestSellingProducts(int topN) {
        return jedis.zrevrangeWithScores("product:sales", 0, topN - 1);
    }

    // Method to add a recent customer
    public void addRecentCustomer(String phone, Date purchaseDate) {
        // Convert java.util.Date to epoch timestamp
        long timestamp = purchaseDate.getTime() / 1000; // Convert milliseconds to seconds

        // Add to the sorted set
        String value = phone + ":" + purchaseDate.toString();
        jedis.zadd("customer:recent", timestamp, value);
    }

    // Method to retrieve the most recent N customers
    public Set<String> getRecentCustomerPurchases(int limit) {
        // Retrieve the most recent purchases from the sorted set
        return jedis.zrevrange("customer:recent", 0, limit - 1);
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

}
