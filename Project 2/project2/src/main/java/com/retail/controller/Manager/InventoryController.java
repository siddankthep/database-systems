package com.retail.controller.Manager;

import com.retail.model.dao.ProductDAO;
import com.retail.model.dao.SupplierDAO;
import com.retail.model.entities.Product;
import com.retail.model.entities.Supplier;
import com.retail.model.services.ProductService;
import com.retail.model.services.SupplierService;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.sql.SQLException;
import java.util.List;

public class InventoryController {
    private ProductService productService;
    private SupplierService supplierService;

    public InventoryController() {
        this.productService = new ProductService(new ProductDAO());
        this.supplierService = new SupplierService(new SupplierDAO());
    }

    public Object[][] fetchProductData() {
        List<Product> products;
        try {
            products = productService.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][];
        }

        Object[][] data = new Object[products.size()][5];
        for (int i = 0; i < products.size(); i++) {
            try {
                Product product = products.get(i);
                Object productID = product.getProductId();
                Object productName = product.getProductName();
                Supplier supplier = supplierService.getSupplierById(product.getSupplierId());
                Object supplierName = supplier.getSupplierName();
                Object price = product.getPrice();
                Object stockQuantity = product.getStockQuantity();
                data[i] = new Object[]{productID, productName, supplierName, price, stockQuantity};
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public void refreshTable(DefaultTableModel model) {
        Object[][] data = fetchProductData();
        String[] columnNames = {"Product ID", "Product Name", "Supplier Name", "Unit Price", "Stock Quantity"};
        model.setDataVector(data, columnNames);
        model.fireTableDataChanged();
    }

        public void filterTableSearch(DefaultTableModel model, String searchTerm, JTable inventoryTable) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        inventoryTable.setRowSorter(sorter);

        if (searchTerm.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchTerm));
        }
    }
}