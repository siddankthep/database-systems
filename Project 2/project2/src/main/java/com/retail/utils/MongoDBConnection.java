package com.retail.utils;

import java.io.FileInputStream;
import java.util.Properties;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    private static MongoDBConnection instance;
    private MongoClient mongoClient;
    private MongoDatabase database;

    // Private constructor to prevent instantiation
    private MongoDBConnection() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            // Load properties from config file
            properties.load(input);

            String uri = properties.getProperty("MONGODB_URL");
            // Connection URI for the local MongoDB server

            // Create the MongoClient and connect to the database
            mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase("myDatabase");
        } catch (Exception e) {
            System.out.println("Error loading config.properties file: " + e.getMessage());
        }
    }

    // Public method to provide access to the single instance
    public static MongoDBConnection getInstance() {
        if (instance == null) {
            synchronized (MongoDBConnection.class) {
                if (instance == null) {
                    instance = new MongoDBConnection();
                }
            }
        }
        return instance;
    }

    // Getter for MongoDatabase
    public MongoDatabase getDatabase() {
        return database;
    }

    // Close the connection when the application is shutting down
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

}
