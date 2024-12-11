package com.retail.controller.Customer;

import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.ReviewDAO;
import com.retail.model.dao.ShipperDAO;
import com.retail.model.entities.CustomerMongo;
import com.retail.model.entities.OrderDetails;
import com.retail.model.entities.OrderMongo;
import com.retail.model.entities.Product;
import com.retail.model.services.ProductService;
import com.retail.model.services.ReviewService;
import com.retail.model.services.ShipperService;
import com.retail.view.Customer.ReviewFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderHistoryController {
    private ShipperService shipperService = new ShipperService(new ShipperDAO());
    private ProductService productService = new ProductService(new ProductDAO());
    private ReviewService reviewService = new ReviewService(new ReviewDAO());

    public JTable createOrderTable(List<OrderMongo> orders, CustomerMongo customer) {
        String[] columnNames = { "Product ID", "Order Date", "Shipper Service", "Product Name", "Quantity", "Price",
                "Payment Status", "Review" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
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
            JOptionPane.showMessageDialog(null, "Error loading orders: " +
                    e.getMessage());
            e.printStackTrace();
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        // table.setEnabled(false); // Disable editing

        // Add custom renderer and editor for the Review column
        table.getColumnModel().getColumn(7).setCellRenderer(new ReviewButtonRenderer(customer.getPhone()));
        table.getColumnModel().getColumn(7)
                .setCellEditor(new ReviewButtonEditor(new JButton("Review"), customer.getPhone()));

        return table;
    }

    private class ReviewButtonRenderer extends JButton implements TableCellRenderer {
        private String customerPhone;
        private Product product;

        public ReviewButtonRenderer(String customerPhone) {
            this.customerPhone = customerPhone;
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText((value == null) ? "Review" : value.toString());

            try {

                // Check if the product has been reviewed
                int productID = Integer.parseInt(table.getValueAt(row, 0).toString());

                product = productService.getProductById(productID);
                if (reviewService.isProductReviewed(product, customerPhone)) {
                    setBackground(Color.GREEN);
                    setText("Reviewed");
                } else {
                    setBackground(UIManager.getColor("Button.background"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return this;
        }
    }

    private class ReviewButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private String customerPhone;
        private Product product;

        public ReviewButtonEditor(JButton button, String customerPhone) {
            super();
            this.button = button;
            this.button.setOpaque(true);
            this.customerPhone = customerPhone;

            this.button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            label = (value == null) ? "Review" : value.toString();
            button.setText(label);
            clicked = true;

            // Fetch product details from the table row
            try {

                int productID = Integer.parseInt(table.getValueAt(row, 0).toString());
                product = productService.getProductById(productID);
                String paymentStatus = table.getValueAt(row, 6).toString();
                if (paymentStatus.equals("Unpaid")) {
                    JOptionPane.showMessageDialog(null, "Please pay for the product before reviewing.");
                    clicked = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                // Check if the product has already been reviewed
                if (reviewService.isProductReviewed(product, customerPhone)) {
                    JOptionPane.showMessageDialog(null, "You have already reviewed this product!");
                } else {
                    // Open the ReviewFrame if not reviewed
                    new ReviewFrame(customerPhone, product).setVisible(true);
                }
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

    }

}
