package com.retail.model.services;

import com.retail.model.dao.ProductDAO;
import java.sql.SQLException;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public String getProductNameById(int productId) throws SQLException {
        return productDAO.getById(productId).getProductName();
    }

    public double getProductPriceById(int productId) throws SQLException {
        return productDAO.getById(productId).getPrice();
    }
}
