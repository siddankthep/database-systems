package com.retail.view.Customer;

import com.retail.model.dao.CustomerDAO;
import com.retail.model.dao.OrderDAO;
import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.ShipperDAO;
import com.retail.model.entities.CustomerMongo;
import com.retail.model.entities.OrderDetails;
import com.retail.model.entities.OrderMongo;
import com.retail.model.entities.Product;
import com.retail.model.services.CustomerService;
import com.retail.model.services.OrderService;
import com.retail.model.services.ProductService;
import com.retail.model.services.ShipperService;
import com.retail.view.components.ButtonEditor;
import com.retail.view.components.ButtonRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class OrderHistoryFrame extends JFrame {
    private OrderService orderService = new OrderService(new OrderDAO());
    private ShipperService shipperService = new ShipperService(new ShipperDAO());
    private CustomerService customerService = new CustomerService(new CustomerDAO());
    private ProductService productService = new ProductService(new ProductDAO());
    JTable orderTable;
    JLabel infoLabel;
    DefaultTableModel tableModel;
    CustomerMongo customer;


    public OrderHistoryFrame(String customerPhone) {
        this.customer = customerService.findCustomerByPhoneMongo(customerPhone);
        setTitle("Order History");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fetch orders for the customer
        List<OrderMongo> orders = orderService.getAllOrdersByPhoneMongo(customerPhone);

        // Create the table to display orders
        orderTable = createOrderTable(orders);
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        
        // Add components to the frame
        add(new JScrollPane(orderTable), BorderLayout.CENTER);
        
        infoLabel = new JLabel( 
            "Order History for Customer: " + customer.getName());
            add(infoLabel, BorderLayout.NORTH);
            
            setVisible(true);
        }
        
        private JTable createOrderTable(List<OrderMongo> orders) {
            String[] columnNames = { "Product ID", "Order Date", "Shipper Service", "Product Name", "Quantity", "Price",
            "Payment Status", "Review" };
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 7; // Make only the last column editable for buttons
                }
            };
            
            try {
                
                for (OrderMongo order : orders) {
                    int shipperId = order.getShipperId();
                    String shipperName = shipperService.getShipperById(shipperId).getShipperName();
                    List<OrderDetails> details = order.getOrderDetails();
                    for (OrderDetails detail : details) {
                        Product product = productService.getProductById(detail.getProductId());
                        tableModel.addRow(new Object[] {
                            product.getProductId(),
                            order.getOrderDate().toString(),
                            shipperName,
                            product.getProductName(),
                            detail.getQuantity(),
                            product.getPrice() * detail.getQuantity(),
                            order.getPayment(),
                            "Review"
                        });
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading orders: " +
                e.getMessage());
                e.printStackTrace();
            }
            
            JTable table = new JTable(tableModel);
            table.setRowHeight(30);
            // table.setEnabled(false); // Disable editing

        // Add custom renderer and editor for the Review column
        table.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JButton("Review"), customer.getPhone()));

        return table;
    }

}
