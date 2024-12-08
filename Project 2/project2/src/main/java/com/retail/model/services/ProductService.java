package com.retail.model.services;

import com.retail.model.dao.ProductDAO;
import com.retail.model.entities.Product;

import java.sql.SQLException;

import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void insertProduct(Product product) throws SQLException {
        productDAO.insert(product);
    }

    public void updateProduct(Product product) throws SQLException {
        productDAO.update(product);
    }

    public void deleteProduct(int productId) throws SQLException {
        productDAO.delete(productId);
    }

    public Product getProductById(int productId) throws SQLException {
        return productDAO.getById(productId);
    }

    public String getProductNameById(int productId) throws SQLException {
        return productDAO.getById(productId).getProductName();
    }

    public double getProductPriceById(int productId) throws SQLException {
        return productDAO.getById(productId).getPrice();
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAll();
    }
}
