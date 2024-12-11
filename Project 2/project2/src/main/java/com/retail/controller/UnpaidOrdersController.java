package com.retail.controller;

import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.retail.model.dao.OrderDAO;
import com.retail.model.entities.OrderMongo;
import com.retail.model.services.OrderService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnpaidOrdersController {
    private OrderService orderService = new OrderService(new OrderDAO());

    private List<OrderMongo> unpaidOrders = orderService.getUnpaidOrdersMongo();

    public JTable createUnpaidOrdersTable() {

        String[] columnNames = { "Order ID", "Customer Phone", "Order Date", "Total Amount", "Payment Status",
                "Checkout" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the "Action" column is editable for buttons
            }
        };

        for (OrderMongo order : unpaidOrders) {
            tableModel.addRow(new Object[] {
                    order.getId(),
                    order.getCustomerPhone(),
                    order.getOrderDate(),
                    order.getTotalAmount(),
                    order.getPayment(),
                    "Checkout"
            });
        }

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);

        table.getColumnModel().getColumn(5).setCellRenderer(new CheckoutButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new CheckoutButtonEditor(new JButton("Checkout")));

        return table;
    }

    private class CheckoutButtonRenderer extends JButton implements TableCellRenderer {
        public CheckoutButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText((value == null) ? "Checkout" : value.toString());
            return this;
        }
    }

    public class CheckoutButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;

        public CheckoutButtonEditor(JButton button) {
            this.button = button;
            this.button.setOpaque(true);

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
            label = (value == null) ? "Checkout" : value.toString();
            button.setText(label);
            clicked = true;
            String orderID = table.getValueAt(row, 0).toString();
            orderService.updateOrderPaymentStatusMongo(orderID, "Paid");
            System.out.println("Order ID: " + orderID + " has been checked out.");

            unpaidOrders = orderService.getUnpaidOrdersMongo();
            refreshTableContent(table);
            JOptionPane.showMessageDialog(null, "Done checking out order!");

            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
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

    private void refreshTableContent(JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (OrderMongo order : unpaidOrders) {
            tableModel.addRow(new Object[] {
                    order.getId(),
                    order.getCustomerPhone(),
                    order.getOrderDate(),
                    order.getTotalAmount(),
                    order.getPayment(),
                    "Checkout"
            });
        }
    }
}
