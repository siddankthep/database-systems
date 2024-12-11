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
            properties.load(input);

            String uri = properties.getProperty("MONGODB_URL");
            String db = properties.getProperty("MONGODB_DATABASE");

            mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase(db);
        } catch (Exception e) {
            System.out.println("Error loading config.properties file: " + e.getMessage());
        }
    }

    // Singleton pattern
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
