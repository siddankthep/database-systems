package com.retail.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.retail.model.entities.Product;
import com.retail.utils.DatabaseConnection;

public class ProductDAO {
    public void insert(Product product) throws SQLException {
        String sql = "INSERT INTO Product (ProductName, Price, Unit, SupplierID, StockQuantity) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getUnit());
            stmt.setInt(4, product.getSupplierId());
            stmt.setInt(5, product.getStockQuantity());
            stmt.executeUpdate();
        }
    }

    public void update(Product product) throws SQLException {
        String sql = "UPDATE Product SET ProductName = ?, Price = ?, Unit = ?, SupplierID = ?, StockQuantity = ? WHERE ProductID = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getUnit());
            stmt.setInt(4, product.getSupplierId());
            stmt.setInt(5, product.getStockQuantity());
            stmt.setInt(6, product.getProductId());
            stmt.executeUpdate();
        }
    }

    public void delete(int productId) throws SQLException {
        String sql = "DELETE FROM Product WHERE ProductID = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        }
    }

    public Product getById(int productId) throws SQLException {
        String sql = "SELECT * FROM Product WHERE ProductID = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getDouble("Price"),
                        rs.getInt("Unit"), rs.getInt("SupplierID"), rs.getInt("StockQuantity"));
            }
        }
        return null;
    }

    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        Connection conn = DatabaseConnection.getConnection();
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(new Product(rs.getInt("ProductID"), rs.getString("ProductName"),
                        rs.getDouble("Price"), rs.getInt("Unit"), rs.getInt("SupplierID"),
                        rs.getInt("StockQuantity")));
            }
        }
        return products;
    }
}
