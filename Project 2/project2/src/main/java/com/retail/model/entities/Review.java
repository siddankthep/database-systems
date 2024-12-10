package com.retail.model.entities;

import org.bson.types.ObjectId;

public class Review {
    private ObjectId id;
    private int rating;
    private String comment;
    private int productId;
    private String customerPhone;

    // Constructors
    public Review() {
    }

    public Review(ObjectId id, int productId, String customerPhone, int rating, String comment) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.productId = productId;
        this.customerPhone = customerPhone;
    }

    public Review(int productId, String customerPhone, int rating, String comment) {
        this.id = null;
        this.rating = rating;
        this.comment = comment;
        this.productId = productId;
        this.customerPhone = customerPhone;
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    // toString method for debugging and display
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", productId=" + productId +
                ", customerPhone='" + customerPhone + '\'' +
                '}';
    }
}
