package com.retail.model.dao;

import com.retail.model.entities.Shipper;
import com.retail.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShipperDAO {

    // Fetch a random shipper ID
    public int fetchRandomShipperId() throws SQLException {
        String query = "SELECT ShipperID FROM Shipper WHERE ShipperName IS NOT NULL ORDER BY RAND() LIMIT 1";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("ShipperID");
            } else {
                throw new SQLException("No shippers found in the database.");
            }
        }
    }

    // Insert a new shipper
    public boolean insertShipper(Shipper shipper) throws SQLException {
        String insertQuery = "INSERT INTO Shipper (ShipperID, ShipperServiceID, ShipperName, ContactPhone) VALUES (?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setInt(1, shipper.getShipperId());
            statement.setString(2, shipper.getShipperServiceId());
            statement.setString(3, shipper.getShipperName());
            statement.setString(4, shipper.getContact());
            return statement.executeUpdate() > 0;
        }
    }

    public Shipper getById(int shipperId) throws SQLException {
        String query = "SELECT * FROM Shipper WHERE ShipperID = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, shipperId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Shipper(
                            resultSet.getInt("ShipperID"),
                            resultSet.getString("ShipperName"),
                            resultSet.getString("ShipperServiceID"),
                            resultSet.getString("ContactPhone"));
                } else {
                    throw new SQLException("No shipper found with ID: " + shipperId);
                }
            }
        }
    }

    public static void main(String[] args) {
        ShipperDAO shipperDAO = new ShipperDAO();
        try {
            // String query = "SELECT * FROM Shipper WHERE ShipperID = 3";
            Shipper shipper = shipperDAO.getById(3);
            System.out.println("Shipper ID: " + shipper.getShipperId() + " Shipper Name: " + shipper.getShipperName()
                    + " Shipper Service ID: " + shipper.getShipperServiceId() + " Contact: " + shipper.getContact());
            // System.out.println(shipperDAO.fetchRandomShipperId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
