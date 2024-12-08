package com.retail.model.services;

import java.sql.SQLException;

import com.retail.model.dao.SupplierOrderDAO;
import com.retail.model.entities.SupplierOrder;
import com.retail.model.entities.SupplierOrderDetails;

public class SupplierOrderService {
    SupplierOrderDAO supplierOrderDAO;

    public SupplierOrderService(SupplierOrderDAO supplierOrderDAO) {
        this.supplierOrderDAO = supplierOrderDAO;

    }

    public int createSupplierOrder(int supplierId, java.util.Date orderDate, double totalAmount) throws SQLException {
        System.out
                .println("Supplier ID: " + supplierId + " Order Date: " + orderDate + " Total Amount: " + totalAmount);
        if (supplierId <= 0 || orderDate == null || totalAmount <= 0) {
            throw new IllegalArgumentException("Invalid supplier order details.");
        }

        // Create a new SupplierOrder object
        SupplierOrder supplierOrder = new SupplierOrder(0, supplierId, orderDate, totalAmount);

        int orderId = supplierOrderDAO.insert(supplierOrder);
        supplierOrder.setSupplierOrderId(orderId); // Set the generated order ID
        return orderId;
    }

    public void addSupplierOrderDetail(int supplierOrderId, int productId, int quantity) throws SQLException {
        if (supplierOrderId <= 0 || productId <= 0 || quantity <= 0) {
            throw new IllegalArgumentException("Invalid supplier order details.");
        }
        SupplierOrderDetails orderDetail = new SupplierOrderDetails(0, supplierOrderId, productId, quantity);
        supplierOrderDAO.addOrderDetail(orderDetail);
    }
}
