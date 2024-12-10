package com.retail.model.services;

import java.sql.SQLException;
import java.util.List;

import com.retail.model.dao.OrderDAO;
import com.retail.model.entities.OrderSQL;
import com.retail.model.entities.OrderDetails;
import com.retail.model.entities.OrderMongo;

import java.util.Date;

public class OrderService {
    private OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public int createOrderSQL(Date orderDate, int customerId, int shipperId, double totalAmount) throws SQLException {

        if (orderDate == null || customerId <= 0 || shipperId < 0 || totalAmount <= 0) {
            System.out.println("Date: " + orderDate + " Customer ID: " + customerId + " Shipper ID: " + shipperId
                    + " Total Amount: " + totalAmount);
            throw new IllegalArgumentException("Invalid order details.");
        }

        // Create a new Order object
        OrderSQL order = new OrderSQL(0, orderDate, customerId, shipperId, totalAmount);

        // Call DAO to insert order
        int orderId = orderDAO.insertSQL(order);
        order.setOrderId(orderId); // Set the generated order ID
        return orderId;
    }

    public void addOrderDetailSQL(OrderDetails orderDetail) throws SQLException {
        orderDAO.addOrderDetailSQL(orderDetail);
    }

    public List<OrderSQL> getAllOrdersSQL() throws SQLException {
        return orderDAO.getAllOrdersSQL();
    }

    public List<OrderDetails> getOrderDetailsSQL(int orderId) throws SQLException {
        return orderDAO.getOrderDetailsSQL(orderId);
    }

    public void createOrderMongo(OrderMongo order) {
        orderDAO.insertMongo(order);
    }

    public List<OrderMongo> getAllOrdersByPhoneMongo(String phone) {
        return orderDAO.getAllOrdersByPhoneMongo(phone);
    }
}
