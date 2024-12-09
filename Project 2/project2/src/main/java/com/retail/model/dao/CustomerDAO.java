package com.retail.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.retail.model.entities.CustomerMongo;
import com.retail.model.entities.CustomerSQL;
import com.retail.utils.MongoDBConnection;
import com.retail.utils.MySQLConnection;

public class CustomerDAO {
    private MongoDatabase mongoDatabase = MongoDBConnection.getInstance().getDatabase();

    public void insertSQL(CustomerSQL customer) throws SQLException {
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

    public void updateSQL(CustomerSQL customer) throws SQLException {
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

    public CustomerSQL getByIdSQL(int customerId) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE CustomerID = ?";
        Connection conn = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CustomerSQL(rs.getInt("CustomerID"), rs.getString("Name"), rs.getString("Phone"),
                        rs.getString("Address"));
            }
        }
        return null;
    }

    public List<CustomerSQL> getAllSQL() throws SQLException {
        List<CustomerSQL> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        Connection conn = MySQLConnection.getConnection();
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(new CustomerSQL(rs.getInt("CustomerID"), rs.getString("Name"), rs.getString("Phone"),
                        rs.getString("Address")));
            }
        }
        return customers;
    }

    public CustomerMongo findCustomerByPhoneMongo(String phone) {
        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("Customers");

            // Query the collection by phone
            Document query = new Document("phone", phone);
            Document doc = customersCollection.find(query).first();

            // Return the found customer or null if not found
            if (doc != null) {
                System.out.println("Customer found: " + doc.toJson());
                ObjectId id = doc.getObjectId("_id");
                String name = doc.getString("name");
                String address = doc.getString("address");

                return new CustomerMongo(id, name, address, phone);
            } else {
                System.out.println("No customer found with phone: " + phone);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertMongo(CustomerMongo customer) {
        try {
            MongoCollection<Document> customersCollection = mongoDatabase.getCollection("Customers");

            // Create a new document
            Document doc = new Document("name", customer.getName())
                    .append("phone", customer.getPhone())
                    .append("address", customer.getAddress());

            // Insert the document
            customersCollection.insertOne(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
