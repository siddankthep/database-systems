package com.retail.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static Connection connection = null;

    // Load properties and create a connection
    private static void initializeConnection() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            // Load properties from config file
            properties.load(input);

            String dbUrl = properties.getProperty("DB_URL");
            String user = properties.getProperty("DB_USER");
            String password = properties.getProperty("DB_PASSWORD");

            // Initialize connection
            connection = DriverManager.getConnection(dbUrl, user, password);
            System.out.println("Database connected successfully.");

        } catch (IOException e) {
            System.out.println("Error loading config.properties file: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    // Get database connection instance (lazy loading)
    public static Connection getConnection() {
        if (connection == null) {
            System.out.println("Connection is null, initializing connection...");
            initializeConnection();
        }
        System.out.println("Connection not null");
        return connection;
    }

    // Close connection method
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }

}
