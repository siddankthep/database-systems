package com.retail.model.services;

import com.retail.model.dao.RedisDAO;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Set;

public class RedisService {
    private RedisDAO redisDAO = new RedisDAO();

    public void incrementProductSales(int productId, double quantity) {
        redisDAO.incrementProductSales(productId, quantity);
    }

    public Set<Tuple> getTopBestSellingProducts(int topN) {

        return redisDAO.getTopBestSellingProducts(topN);
    }

    public void addRecentCustomer(String customerPhone) {
        redisDAO.addRecentCustomer(customerPhone);
    }

    public List<String> getRecentCustomers(int limit) {
        return redisDAO.getRecentCustomers(limit);
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
