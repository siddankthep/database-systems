package com.retail.model.entities;

public class SupplierOrder {
    private int supplierOrderId;
    private int supplierId;
    private java.util.Date orderDate;
    private double totalAmount;

    public SupplierOrder(int supplierOrderId, int supplierId, java.util.Date orderDate, double totalAmount) {
        this.supplierOrderId = supplierOrderId;
        this.supplierId = supplierId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public int getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(int supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public java.util.Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(java.util.Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

}
