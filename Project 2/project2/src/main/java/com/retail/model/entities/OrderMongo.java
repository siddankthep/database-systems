package com.retail.model.entities;

import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

public class OrderMongo {
    private ObjectId id;
    private Date orderDate;
    private int shipperId;
    private double totalAmount;
    private String payment;
    private List<OrderDetails> orderDetails;
    private String customerPhone;

    // Constructors
    public OrderMongo(ObjectId id, Date orderDate, int shipperId, double totalAmount, String payment,
            List<OrderDetails> orderDetails, String customerPhone) {
        this.id = id;
        this.orderDate = orderDate;
        this.shipperId = shipperId;
        this.totalAmount = totalAmount;
        this.payment = payment;
        this.orderDetails = orderDetails;
        this.customerPhone = customerPhone;
    }

    public OrderMongo(Date orderDate, int shipperId, double totalAmount, String payment,
            List<OrderDetails> orderDetails, String customerPhone) {
        this.id = null;
        this.orderDate = orderDate;
        this.shipperId = shipperId;
        this.totalAmount = totalAmount;
        this.payment = payment;
        this.orderDetails = orderDetails;
        this.customerPhone = customerPhone;
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

}
