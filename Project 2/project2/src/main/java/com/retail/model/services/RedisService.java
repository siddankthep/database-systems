package com.retail.model.services;

import com.retail.model.dao.RedisDAO;
import redis.clients.jedis.Tuple;

import java.util.Date;
import java.util.Set;

public class RedisService {
    private RedisDAO redisDAO = new RedisDAO();

    public void incrementProductSales(String productName, double quantity) {
        redisDAO.incrementProductSales(productName, quantity);
    }

    public Set<Tuple> getTopBestSellingProducts(int topN) {

        return redisDAO.getTopBestSellingProducts(topN);
    }

    public void addRecentCustomer(String customerPhone, Date purchaseDate) {
        redisDAO.addRecentCustomer(customerPhone, purchaseDate);
    }

    public Set<String> getRecentCustomerPurchases(int limit) {
        return redisDAO.getRecentCustomerPurchases(limit);
    }

    public void setCustomerDetails(String customerPhone, String field, String value) {
        redisDAO.setCustomerDetails(customerPhone, field, value);
    }

    public String getCustomerDetail(String customerPhone, String field) {
        return redisDAO.getCustomerDetail(customerPhone, field);
    }

    public Set<String> getAllCustomerFields(String customerPhone) {
        return redisDAO.getAllCustomerFields(customerPhone);
    }

}
