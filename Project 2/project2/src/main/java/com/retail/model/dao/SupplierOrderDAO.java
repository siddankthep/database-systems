package com.retail.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.retail.model.entities.SupplierOrder;
import com.retail.model.entities.SupplierOrderDetails;
import com.retail.utils.MySQLConnection;

public class SupplierOrderDAO {

    public int insert(SupplierOrder order) throws SQLException {
        String sql = "INSERT INTO SupplierOrder (SupplierId, orderDate, totalAmount) VALUES (?, ?, ?)";
        Connection connection = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getSupplierId());
            stmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setDouble(3, order.getTotalAmount());
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

    public void addOrderDetail(SupplierOrderDetails orderDetail) throws SQLException {
        String sql = "INSERT INTO SupplierOrderDetails (SupplierOrderId, productId, quantity) VALUES (?, ?, ?)";
        Connection connection = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderDetail.getSupplierOrderId());
            stmt.setInt(2, orderDetail.getProductId());
            stmt.setInt(3, orderDetail.getQuantity());
            stmt.executeUpdate();
        }
    }

    public List<SupplierOrder> getAllOrders() throws SQLException {
        List<SupplierOrder> orders = new ArrayList<>();
        String sql = "SELECT * FROM SupplierOrder";
        Connection connection = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SupplierOrder order = new SupplierOrder(
                        rs.getInt("SupplierOrderId"),
                        rs.getInt("SupplierId"),
                        rs.getDate("orderDate"),
                        rs.getDouble("totalAmount"));
                orders.add(order);
            }
        }
        return orders;
    }

    public List<SupplierOrderDetails> getOrderDetails(int supplierOrderId) throws SQLException {
        List<SupplierOrderDetails> orderDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM SupplierOrderDetails WHERE SupplierOrderId = ?";
        Connection connection = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, supplierOrderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SupplierOrderDetails orderDetails = new SupplierOrderDetails(
                            rs.getInt("SupplierOrderDetailId"),
                            rs.getInt("SupplierOrderId"),
                            rs.getInt("productId"),
                            rs.getInt("quantity"));
                    orderDetailsList.add(orderDetails);
                }
            }
        }
        return orderDetailsList;
    }
}
