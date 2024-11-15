package com.nk.clothify_backend.service;

import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.model.Rating;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;
    public List<Rating> getProductsRating(Long productId);
}
