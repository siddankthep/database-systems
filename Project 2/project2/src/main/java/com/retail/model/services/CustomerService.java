package com.retail.model.services;

import java.sql.SQLException;

import com.retail.model.dao.CustomerDAO;
import com.retail.model.entities.CustomerMongo;
import com.retail.model.entities.CustomerSQL;

public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public void createCustomerSQL(String name, String phone, String address) throws SQLException {
        // Validate the inputs
        if (name == null || name.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty() ||
                address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Name, phone, and address cannot be empty.");
        }

        // Create a new Customer object
        CustomerSQL customer = new CustomerSQL(0, name, phone, address);

        // Save customer to the database
        customerDAO.insertSQL(customer);
    }

    public CustomerSQL getCustomerByIdSQL(int customerId) throws SQLException {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Invalid customer ID.");
        }

        return customerDAO.getByIdSQL(customerId);
    }

    public CustomerMongo findCustomerByPhoneMongo(String phone)  {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty.");
        }

        return customerDAO.findCustomerByPhoneMongo(phone);
    }

    public void createCustomerMongo(String name, String phone, String address) throws IllegalArgumentException {
        // Validate the inputs
        if (name == null || name.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty() ||
                address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Name, phone, and address cannot be empty.");
        }

        // Create a new Customer object
        CustomerMongo customer = new CustomerMongo(name, phone, address);

        // Save customer to the database
        customerDAO.insertMongo(customer);
    }
}
