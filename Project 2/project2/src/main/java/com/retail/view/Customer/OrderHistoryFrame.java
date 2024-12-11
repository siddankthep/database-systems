package com.retail.view.Customer;

import com.retail.controller.Customer.OrderHistoryController;
import com.retail.model.dao.CustomerDAO;
import com.retail.model.dao.OrderDAO;
import com.retail.model.entities.CustomerMongo;
import com.retail.model.entities.OrderMongo;
import com.retail.model.services.CustomerService;
import com.retail.model.services.OrderService;

import javax.swing.*;

import java.awt.*;
import java.util.List;

public class OrderHistoryFrame extends JFrame {
    private OrderService orderService = new OrderService(new OrderDAO());
    private CustomerService customerService = new CustomerService(new CustomerDAO());
    private OrderHistoryController orderHistoryController = new OrderHistoryController();
    private JTable orderTable;
    private JLabel infoLabel;
    private CustomerMongo customer;

    public OrderHistoryFrame(String customerPhone) {
        setTitle("Order History");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fetch orders for the customer
        customer = customerService.findCustomerByPhoneMongo(customerPhone);
        List<OrderMongo> orders = orderService.getAllOrdersByPhoneMongo(customerPhone);

        // Create the table to display orders
        orderTable = orderHistoryController.createOrderTable(orders, customer);
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(150);

        // Add components to the frame
        add(new JScrollPane(orderTable), BorderLayout.CENTER);

        infoLabel = new JLabel(
                "Order History for Customer: " + customer.getName());
        add(infoLabel, BorderLayout.NORTH);

        setVisible(true);
    }

}
