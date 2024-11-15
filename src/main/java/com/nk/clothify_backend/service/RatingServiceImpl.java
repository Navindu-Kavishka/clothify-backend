package com.nk.clothify_backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.clothify_backend.entity.ProductEntity;
import com.nk.clothify_backend.entity.RatingEntity;
import com.nk.clothify_backend.entity.UserEntity;
import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.model.Product;
import com.nk.clothify_backend.model.Rating;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.repository.RatingRepository;
import com.nk.clothify_backend.request.RatingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService{

    private final RatingRepository ratingRepository;
    private final ProductService productService;
    private final ObjectMapper mapper;

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {

        Product product = productService.findProductById(req.getProductId());

        Rating rating = new Rating();
        rating.setProductEntity(mapper.convertValue(product, ProductEntity.class));
        rating.setUserEntity(mapper.convertValue(user, UserEntity.class));
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return mapper.convertValue(
                ratingRepository.save(
                        mapper.convertValue(rating, RatingEntity.class)
                ), Rating.class
        );
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {

        List<RatingEntity> allProductsRatingEntities = ratingRepository.getAllProductsRating(productId);
        List<Rating> allProducRatingList = new ArrayList<>();

        allProductsRatingEntities.forEach(ratingEntity ->
            allProducRatingList.add(mapper.convertValue(ratingEntity, Rating.class))
        );
        return allProducRatingList;
    }
}
