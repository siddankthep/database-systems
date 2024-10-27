package com.retail.model.services;

import java.sql.SQLException;
import java.util.List;

import com.retail.model.dao.OrderDAO;
import com.retail.model.entities.Order;
import com.retail.model.entities.OrderDetails;

import java.util.Date;

public class OrderService {
    private OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public int createOrder(Date orderDate, int customerId, int shipperId, double totalAmount,
            String paymentStatus) throws SQLException {

        if (orderDate == null || customerId <= 0 || shipperId < 0 || totalAmount <= 0
                || paymentStatus.trim().isEmpty()) {
            System.out.println("Date: " + orderDate + " Customer ID: " + customerId + " Shipper ID: " + shipperId
                    + " Total Amount: " + totalAmount + " Payment Status: " + paymentStatus);
            throw new IllegalArgumentException("Invalid order details.");
        }

        // Create a new Order object
        Order order = new Order(0, orderDate, customerId, shipperId, totalAmount, paymentStatus);

        // Call DAO to insert order
        int orderId = orderDAO.insert(order);
        order.setOrderId(orderId); // Set the generated order ID
        return orderId;
    }

    public void addOrderDetail(OrderDetails orderDetail) throws SQLException {
        orderDAO.addOrderDetail(orderDetail);
    }

    public List<Order> getAllOrders() throws SQLException {
        return orderDAO.getAllOrders();
    }

    public List<OrderDetails> getOrderDetails(int orderId) throws SQLException {
        return orderDAO.getOrderDetails(orderId);
    }
}
