package com.retail.model.dao;

import com.retail.model.entities.Supplier;
import com.retail.utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDAO {
    public Supplier getSupplierById(int supplierId) throws SQLException {
        String sql = "SELECT * FROM Supplier WHERE SupplierId = ?";
        Connection connection = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Supplier(
                            rs.getInt("SupplierId"),
                            rs.getString("SupplierName"),
                            rs.getString("ProductCategory"),
                            rs.getString("ContactPerson"),
                            rs.getString("Address"),
                            rs.getString("Phone"));
                } else {
                    return null;
                }
            }
        }
    }
}
