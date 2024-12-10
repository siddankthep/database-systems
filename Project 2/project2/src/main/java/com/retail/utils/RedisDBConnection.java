package com.retail.utils;

import redis.clients.jedis.Jedis;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RedisDBConnection {

    private static RedisDBConnection instance;
    private Jedis jedis;

    // Private constructor to prevent instantiation
    private RedisDBConnection() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            // Load configuration from properties file
            properties.load(input);
            String host = properties.getProperty("REDIS_HOST");
            int port = Integer.parseInt(properties.getProperty("REDIS_PORT"));

            // Create a Jedis connection
            this.jedis = new Jedis(host, port);
            System.out.println("Connected to Redis on " + host + ":" + port);

        } catch (IOException e) {
            System.err.println("Error loading config.properties: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error connecting to Redis: " + e.getMessage());
        }
    }

    // Public method to get the singleton instance
    public static RedisDBConnection getInstance() {
        if (instance == null) {
            synchronized (RedisDBConnection.class) {
                if (instance == null) {
                    instance = new RedisDBConnection();
                }
            }
        }
        return instance;
    }

    // Method to get the Jedis connection
    public Jedis getJedis() {
        return jedis;
    }

    public static void main(String[] args) {
        RedisDBConnection redisDBConnection = RedisDBConnection.getInstance();
        Jedis jedis = redisDBConnection.getJedis();
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println(value);
    }


}
