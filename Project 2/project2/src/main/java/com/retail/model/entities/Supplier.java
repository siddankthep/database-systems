package com.retail.model.entities;

public class Supplier {
    private int supplierId;
    private String supplierName;
    private String productCategory;
    private String contactPerson;
    private String address;
    private String phone;

    public Supplier(int supplierId, String supplierName, String productCategory, String contactPerson, String address, String phone) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.productCategory = productCategory;
        this.contactPerson = contactPerson;
        this.address = address;
        this.phone = phone;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
}
