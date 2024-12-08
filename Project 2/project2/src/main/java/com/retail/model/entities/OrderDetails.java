package com.retail.model.entities;

public class OrderDetails {
    private int orderDetailId; // Unique ID for the order detail (optional, depending on your database design)
    private int orderId;        // Foreign key referencing the associated order
    private int productId;      // Foreign key referencing the associated product
    private int quantity;       // Quantity of the product ordered

    // Constructor with orderDetailId for retrieval from the database
    public OrderDetails(int orderDetailId, int orderId, int productId, int quantity) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Constructor without orderDetailId (for new order details)
    public OrderDetails(int orderId, int productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

