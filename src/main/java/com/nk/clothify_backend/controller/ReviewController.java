package com.nk.clothify_backend.controller;

import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.exception.UserException;
import com.nk.clothify_backend.model.Review;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.request.ReviewRequest;
import com.nk.clothify_backend.service.ReviewService;
import com.nk.clothify_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview (@RequestBody ReviewRequest req,
                                                @RequestHeader("Authorization")String jwt)
    throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);

        Review review = reviewService.createReview(req, user);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductsReview (@PathVariable Long productId) throws UserException, ProductException {
        List<Review> reviews = reviewService.getAllReview(productId);

        return new ResponseEntity<>(reviews,HttpStatus.ACCEPTED);
    }

}
