package com.retail.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.retail.model.entities.Order;
import com.retail.model.entities.OrderDetails;
import com.retail.utils.MySQLConnection;

public class OrderDAO {

    public int insertSQL(Order order) throws SQLException {
        String sql = "INSERT INTO `Order` (OrderDate, CustomerId, ShipperId, TotalAmount) VALUES (?, ?, ?, ?)";
        Connection connection = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setInt(2, order.getCustomerId());
            stmt.setInt(3, order.getShipperId());
            stmt.setDouble(4, order.getTotalAmount());
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

    public void addOrderDetailSQL(OrderDetails orderDetail) throws SQLException {
        String sql = "INSERT INTO OrderDetails (OrderId, ProductID, Quantity) VALUES (?, ?, ?)";
        Connection connection = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderDetail.getOrderId());
            stmt.setInt(2, orderDetail.getProductId());
            stmt.setInt(3, orderDetail.getQuantity());
            stmt.executeUpdate();
        }
    }

    public List<Order> getAllOrdersSQL() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `Order`";
        Connection connection = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("orderId"),
                        rs.getDate("orderDate"),
                        rs.getInt("customerId"),
                        rs.getInt("shipperId"),
                        rs.getDouble("totalAmount"));
                orders.add(order);
            }
        }
        return orders;
    }

    public List<OrderDetails> getOrderDetailsSQL(int orderId) throws SQLException {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails WHERE orderId = ?";
        Connection connection = MySQLConnection.getConnection();
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
