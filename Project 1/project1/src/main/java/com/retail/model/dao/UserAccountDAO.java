package com.retail.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.retail.model.entities.UserAccount;
import com.retail.utils.DatabaseConnection;

public class UserAccountDAO {

    // Retrieve user information by username
    public UserAccount getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM UserAccount WHERE Username = ?";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("UserID");
                    String password = resultSet.getString("PasswordHash");
                    int roleID = resultSet.getInt("RoleID");
                    java.sql.Date sqlCreatedAt = resultSet.getDate("CreatedAt");
                    // java.sql.Date sqlLastLogin = resultSet.getDate("LastLogin");

                    java.util.Date createdAt = new java.util.Date(sqlCreatedAt.getTime());
                    java.util.Date lastLogin = new java.util.Date();

                    return new UserAccount(userId, username, password, roleID, createdAt, lastLogin);
                }
            }
        }
        return null; // Return null if no user found
    }

    // Create a new user
    public void insert(UserAccount user) throws SQLException {
        String query = "INSERT INTO UserAccount (Username, PasswordHash, RoleID, CreatedAt, LastLogin) VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
            statement.setInt(3, user.getRoleId());
            statement.setDate(4, new java.sql.Date(user.getCreatedAt().getTime()));
            statement.setNull(5, java.sql.Types.DATE);
            statement.executeUpdate();
        }
    }
}
