package com.retail.model.entities;

public class Shipper {
    private int shipperId;
    private String shipperName;
    private String shipperServiceID;
    private String contact;

    public Shipper(int shipperId, String name, String shipperServiceID, String contact) {
        this.shipperId = shipperId;
        this.shipperName = name;
        this.shipperServiceID = shipperServiceID;
        this.contact = contact;
    }

    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperServiceId() {
        return shipperServiceID;
    }

    public void setShipperServiceId(String shipperServiceID) {
        this.shipperServiceID = shipperServiceID;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    
}
