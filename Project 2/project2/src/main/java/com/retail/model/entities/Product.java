package com.retail.model.entities;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int unit;
    private int supplierId;
    private int stockQuantity;
    private int itemsSold;

    public Product(int productId, String productName, double price, int unit, int supplierId, int stockQuantity, int itemsSold) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.unit = unit;
        this.supplierId = supplierId;
        this.stockQuantity = stockQuantity;
        this.itemsSold = itemsSold;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(int itemsSold) {
        this.itemsSold = itemsSold;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", unit=" + unit +
                ", supplierId=" + supplierId +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
