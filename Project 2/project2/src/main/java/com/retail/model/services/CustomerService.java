package com.retail.model.services;

import java.sql.SQLException;

import com.retail.model.dao.CustomerDAO;
import com.retail.model.entities.Customer;

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
        Customer customer = new Customer(0, name, phone, address);

        // Save customer to the database
        customerDAO.insertSQL(customer);
    }

    public Customer getCustomerByIdSQL(int customerId) throws SQLException {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Invalid customer ID.");
        }

        return customerDAO.getByIdSQL(customerId);
    }
}
