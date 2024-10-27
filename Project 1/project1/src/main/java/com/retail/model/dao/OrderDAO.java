package com.retail.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.retail.model.entities.Order;
import com.retail.model.entities.OrderDetails;
import com.retail.utils.DatabaseConnection;

public class OrderDAO {

    public int insert(Order order) throws SQLException {
        String sql = "INSERT INTO `Order` (orderDate, customerId, shipperId, totalAmount, paymentStatus) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setInt(2, order.getCustomerId());
            stmt.setInt(3, order.getShipperId());
            stmt.setDouble(4, order.getTotalAmount());
            stmt.setString(5, order.getPaymentStatus());
            stmt.executeUpdate();

            // Get the generated order ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }
        }
    }

    public void addOrderDetail(OrderDetails orderDetail) throws SQLException {
        String sql = "INSERT INTO OrderDetails (orderId, productId, quantity) VALUES (?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderDetail.getOrderId());
            stmt.setInt(2, orderDetail.getProductId());
            stmt.setInt(3, orderDetail.getQuantity());
            stmt.executeUpdate();
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        Connection connection = DatabaseConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("orderId"),
                        rs.getDate("orderDate"),
                        rs.getInt("customerId"),
                        rs.getInt("shipperId"),
                        rs.getDouble("totalAmount"),
                        rs.getString("paymentStatus"));
                orders.add(order);
            }
        }
        return orders;
    }

    public List<OrderDetails> getOrderDetails(int orderId) throws SQLException {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails WHERE orderId = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderDetails orderDetails = new OrderDetails(
                            rs.getInt("orderDetailId"),
                            rs.getInt("orderId"),
                            rs.getInt("productId"),
                            rs.getInt("quantity"));
                    orderDetailsList.add(orderDetails);
                }
            }
        }
        return orderDetailsList;
    }
}
