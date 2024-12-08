package com.retail.model.entities;

import java.util.Date;

public class Order {
    private int orderId;
    private Date orderDate;
    private int customerId;
    private int shipperId;
    private double totalAmount;

    public Order(int orderId, Date orderDate, int customerId, int shipperId, double totalAmount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.shipperId = shipperId;
        this.totalAmount = totalAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", customerId=" + customerId +
                ", shipperId=" + shipperId +
                ", totalAmount=" + totalAmount + '\'' +
                '}';
    }
}
