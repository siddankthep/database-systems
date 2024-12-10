package com.retail.model.dao;

import java.util.List;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.retail.model.entities.Product;
import com.retail.model.entities.Review;
import com.retail.utils.MongoDBConnection;

public class ReviewDAO {
    MongoDatabase database = MongoDBConnection.getInstance().getDatabase();

    public void insertReview(Review review) {
        try {
            MongoCollection<Document> reviewsCollection = database.getCollection("Reviews");

            // Create a MongoDB document from the Review object
            Document reviewDoc = new Document()
                    .append("rating", review.getRating())
                    .append("comment", review.getComment())
                    .append("productId", review.getProductId())
                    .append("customerPhone", review.getCustomerPhone());

            // Insert the document into the collection
            reviewsCollection.insertOne(reviewDoc);

            System.out.println("Review inserted successfully: " + reviewDoc.toJson());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error inserting review: " + e.getMessage());
        }
    }

    public List<Review> getAllReviewsByProduct(Product product) {
        List<Review> reviews = new ArrayList<>();
        try {
            MongoCollection<Document> reviewsCollection = database.getCollection("Reviews");

            // Create a filter to get reviews by product ID
            Document filter = new Document("productId", product.getProductId());

            // Get all reviews for the product
            for (Document reviewDoc : reviewsCollection.find(filter)) {
                Review review = new Review();
                review.setRating(reviewDoc.getInteger("rating"));
                review.setComment(reviewDoc.getString("comment"));
                review.setProductId(reviewDoc.getInteger("productId"));
                review.setCustomerPhone(reviewDoc.getString("customerPhone"));
                reviews.add(review);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error getting reviews: " + e.getMessage());
        }

        return reviews;
    }

        public boolean isProductReviewed(int productId, String customerPhone) {
            try {
            MongoCollection<Document> reviewsCollection = database.getCollection("Reviews");

            // Query for an existing review
            Document query = new Document("productId", productId).append("customerPhone", customerPhone);
            return reviewsCollection.find(query).first() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
