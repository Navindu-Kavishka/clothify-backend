package com.nk.clothify_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.clothify_backend.entity.ProductEntity;
import com.nk.clothify_backend.entity.ReviewEntity;
import com.nk.clothify_backend.entity.UserEntity;
import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.model.Product;
import com.nk.clothify_backend.model.Review;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.repository.ProductRepository;
import com.nk.clothify_backend.repository.ReviewRepository;
import com.nk.clothify_backend.request.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ObjectMapper mapper;

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {

        Product product = productService.findProductById(req.getProductId());

        Review review = new Review();
        review.setUserEntity(mapper.convertValue(user, UserEntity.class));
        review.setProductEntity(mapper.convertValue(product, ProductEntity.class));
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());

        return mapper.convertValue(
                reviewRepository.save(
                        mapper.convertValue(review, ReviewEntity.class)
                ), Review.class
        );
    }

    @Override
    public List<Review> getAllReview(Long productId) {

        List<ReviewEntity> allProductsReviewEntityList = reviewRepository.getAllProductsReview(productId);
        List<Review> allProductReviewList = new ArrayList<>();

        allProductsReviewEntityList.forEach(reviewEntity -> allProductReviewList.add(
                mapper.convertValue(reviewEntity,Review.class)
        ));

        return allProductReviewList;
    }
}
