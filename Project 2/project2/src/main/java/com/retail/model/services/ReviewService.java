package com.retail.model.services;

import com.retail.model.dao.ReviewDAO;
import com.retail.model.entities.Product;
import com.retail.model.entities.Review;

public class ReviewService {
    private ReviewDAO reviewDAO;

    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    public void addReview(Review review) {
        reviewDAO.insertReview(review);
    }

    public void getReviewsByProduct(Product product) {
        reviewDAO.getAllReviewsByProduct(product);
    }

    public boolean isProductReviewed(Product product, String phone) {
        return reviewDAO.isProductReviewed(product.getProductId(), phone);
    }

}
