package com.nk.clothify_backend.service;

import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.model.Review;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getAllReview(Long productId);
}
