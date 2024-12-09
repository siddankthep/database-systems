package com.retail.model.entities;

import org.bson.types.ObjectId;

public class CustomerMongo {
    private ObjectId id;  // Corresponds to _id
    private String name;  // Corresponds to name
    private String address;  // Corresponds to address
    private String phone;  // Corresponds to phone

    // Constructors
    public CustomerMongo(ObjectId id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public CustomerMongo(String name, String phone, String address) {
        this.id = null;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
