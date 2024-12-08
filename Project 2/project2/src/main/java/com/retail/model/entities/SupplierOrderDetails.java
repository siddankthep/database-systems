package com.retail.model.entities;

public class SupplierOrderDetails {
    private int supplierOrderDetailsId;
    private int supplierOrderId;
    private int productId;
    private int quantity;

    public SupplierOrderDetails(int supplierOrderDetailsId, int supplierOrderId, int productId, int quantity) {
        this.supplierOrderDetailsId = supplierOrderDetailsId;
        this.supplierOrderId = supplierOrderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getSupplierOrderDetailsId() {
        return supplierOrderDetailsId;
    }

    public void setSupplierOrderDetailsId(int supplierOrderDetailsId) {
        this.supplierOrderDetailsId = supplierOrderDetailsId;
    }

    public int getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(int supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
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
