package com.retail.model.services;

import com.retail.model.dao.SupplierDAO;
import com.retail.model.entities.Supplier;

import java.sql.SQLException;

public class SupplierService {
    private final SupplierDAO supplierDAO;

    public SupplierService(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    public Supplier getSupplierById(int supplierId) throws SQLException {
        return supplierDAO.getSupplierById(supplierId);
    }

}
