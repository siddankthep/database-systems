package com.retail.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.retail.model.entities.Customer;
import com.retail.utils.MySQLConnection;

public class CustomerDAO {
    public void insertSQL(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer (Name, Phone, Address) VALUES (?, ?, ?)";
        Connection conn = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhone());
            stmt.setString(3, customer.getAddress());
            stmt.executeUpdate();
        }
    }

    public void updateSQL(Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET Name = ?, Phone = ?, Address = ? WHERE CustomerID = ?";
        Connection conn = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhone());
            stmt.setString(3, customer.getAddress());
            stmt.setInt(4, customer.getCustomerId());
            stmt.executeUpdate();
        }
    }

    public void deleteSQL(int customerId) throws SQLException {
        String sql = "DELETE FROM Customer WHERE CustomerID = ?";
        Connection conn = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        }
    }

    public Customer getByIdSQL(int customerId) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE CustomerID = ?";
        Connection conn = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getInt("CustomerID"), rs.getString("Name"), rs.getString("Phone"),
                        rs.getString("Address"));
            }
        }
        return null;
    }

    public List<Customer> getAllSQL() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        Connection conn = MySQLConnection.getConnection();
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(new Customer(rs.getInt("CustomerID"), rs.getString("Name"), rs.getString("Phone"),
                        rs.getString("Address")));
            }
        }
        return customers;
    }
}