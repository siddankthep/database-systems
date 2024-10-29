package com.retail.view;

import javax.swing.*;

import com.retail.model.dao.CustomerDAO;
import com.retail.model.dao.OrderDAO;
import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.ShipperDAO;
import com.retail.model.services.CustomerService;
import com.retail.model.services.OrderService;
import com.retail.model.services.ProductService;
import com.retail.model.services.ShipperService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeMenu extends JFrame {

    public EmployeeMenu() {
        setTitle("Employee Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JButton createOrderButton = new JButton("Create Order");
        JButton createCustomerButton = new JButton("Create Customer");

        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateOrderFrame(new OrderService(new OrderDAO()), new ProductService(new ProductDAO()),
                        new ShipperService(new ShipperDAO()), new CustomerService(new CustomerDAO()));
            }
        });

        createCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateCustomerFrame(new CustomerService(new CustomerDAO()));
            }
        });

        panel.add(createOrderButton);
        panel.add(createCustomerButton);
        add(panel);

        setVisible(true);
    }
}
