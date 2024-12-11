package com.retail.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.retail.model.entities.OrderSQL;
import com.retail.model.entities.OrderDetails;
import com.retail.model.entities.OrderMongo;
import com.retail.utils.MongoDBConnection;
import com.retail.utils.MySQLConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class OrderDAO {
    MongoDatabase mongoDatabase = MongoDBConnection.getInstance().getDatabase();

    public int insertSQL(OrderSQL order) throws SQLException {
        String sql = "INSERT INTO `Order` (OrderDate, CustomerId, ShipperId, TotalAmount) VALUES (?, ?, ?, ?)";
        Connection connection = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setInt(2, order.getCustomerId());
            stmt.setInt(3, order.getShipperId());
            stmt.setDouble(4, order.getTotalAmount());
            stmt.executeUpdate();

            // Get the generated order ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }
        }
    }

    public void addOrderDetailSQL(OrderDetails orderDetail) throws SQLException {
        String sql = "INSERT INTO OrderDetails (OrderId, ProductID, Quantity) VALUES (?, ?, ?)";
        Connection connection = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderDetail.getOrderId());
            stmt.setInt(2, orderDetail.getProductId());
            stmt.setInt(3, orderDetail.getQuantity());
            stmt.executeUpdate();
        }
    }

    public List<OrderSQL> getAllOrdersSQL() throws SQLException {
        List<OrderSQL> orders = new ArrayList<>();
        String sql = "SELECT * FROM `Order`";
        Connection connection = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrderSQL order = new OrderSQL(
                        rs.getInt("orderId"),
                        rs.getDate("orderDate"),
                        rs.getInt("customerId"),
                        rs.getInt("shipperId"),
                        rs.getDouble("totalAmount"));
                orders.add(order);
            }
        }
        return orders;
    }

    public List<OrderDetails> getOrderDetailsSQL(int orderId) throws SQLException {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails WHERE orderId = ?";
        Connection connection = MySQLConnection.getConnection();
        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderDetails orderDetails = new OrderDetails(
                            rs.getInt("orderDetailId"),
                            rs.getInt("orderId"),
                            rs.getInt("productId"),
                            rs.getInt("quantity"));
                    orderDetailsList.add(orderDetails);
                }
            }
        }
        return orderDetailsList;
    }

    public void insertMongo(OrderMongo order) {
        try {

            MongoCollection<Document> orderCollection = mongoDatabase.getCollection("Order");
            // Convert Order object to MongoDB Document
            Document orderDoc = new Document()
                    .append("CustomerPhone", order.getCustomerPhone())
                    .append("OrderDate", order.getOrderDate())
                    .append("payment", order.getPayment())
                    .append("ShipperID", order.getShipperId())
                    .append("TotalAmount", order.getTotalAmount());

            // Convert OrderDetails to a List of Documents
            List<Document> orderDetailsDocs = new ArrayList<>();
            for (OrderDetails detail : order.getOrderDetails()) {
                Document detailDoc = new Document()
                        .append("ProductID", detail.getProductId())
                        .append("Quantity", detail.getQuantity());
                orderDetailsDocs.add(detailDoc);
            }

            // Add OrderDetails to the Order Document
            orderDoc.append("OrderDetails", orderDetailsDocs);

            // Insert the document into the collection
            orderCollection.insertOne(orderDoc);

            System.out.println("Order inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<OrderMongo> getAllOrdersByPhoneMongo(String customerPhone) {
        List<OrderMongo> orders = new ArrayList<>();

        try {
            MongoCollection<Document> orderCollection = mongoDatabase.getCollection("Order");

            // Query to fetch orders for the given customer phone
            Document query = new Document("CustomerPhone", customerPhone);
            MongoCursor<Document> cursor = orderCollection.find(query).iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                orders.add(mapDocumentToOrder(doc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
    
    public List<OrderMongo> getAllOrdersMongo() {
        List<OrderMongo> orders = new ArrayList<>();

        try {
            MongoCollection<Document> orderCollection = mongoDatabase.getCollection("Order");

            // Query to fetch orders for the given customer phone
            MongoCursor<Document> cursor = orderCollection.find().iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                orders.add(mapDocumentToOrder(doc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<OrderMongo> getUnpaidOrdersMongo() {
        List<OrderMongo> unpaidOrders = new ArrayList<>();

        try  {
            // Access the database and collection
            MongoCollection<Document> ordersCollection = mongoDatabase.getCollection("Order");

            // Query to find unpaid orders
            Document query = new Document("payment", "Unpaid");
            MongoCursor<Document> cursor = ordersCollection.find(query).iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                unpaidOrders.add(mapDocumentToOrder(doc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return unpaidOrders;
    }

    private OrderMongo mapDocumentToOrder(Document doc) {
        ObjectId id = doc.getObjectId("_id");
        Date orderDate = doc.getDate("OrderDate");
        int shipperId = doc.getInteger("ShipperID", 0);
        double totalAmount = doc.getDouble("TotalAmount");
        String payment = doc.getString("payment");
        String customerPhone = doc.getString("CustomerPhone");

        // Map OrderDetails
        List<OrderDetails> orderDetails = new ArrayList<>();
        List<Document> details = doc.getList("OrderDetails", Document.class);
        if (details != null) {
            for (Document detailDoc : details) {
                int productId = detailDoc.getInteger("ProductID");
                int quantity = detailDoc.getInteger("Quantity");
                orderDetails.add(new OrderDetails(productId, quantity));
            }
        }

        return new OrderMongo(id, orderDate, shipperId, totalAmount, payment, orderDetails, customerPhone);
    }

    public void updateOrderPaymentStatusMongo(String orderId, String paymentStatus) {
        try {
            // Access the database and collection
            MongoCollection<Document> ordersCollection = mongoDatabase.getCollection("Order");

            // Query to find the order by ID
            Document query = new Document("_id", new ObjectId(orderId));
            Document update = new Document("$set", new Document("payment", paymentStatus));

            // Update the payment status
            ordersCollection.updateOne(query, update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}