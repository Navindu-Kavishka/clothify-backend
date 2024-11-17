package com.nk.clothify_backend.controller;


import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.exception.UserException;
import com.nk.clothify_backend.model.Rating;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.request.RatingRequest;
import com.nk.clothify_backend.service.RatingService;
import com.nk.clothify_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final UserService userService;
    private final RatingService ratingService;


    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req, @RequestHeader("Authorization")String jwt)
        throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);

        Rating rating = ratingService.createRating(req, user);

        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/prduct/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating (@PathVariable Long productId,
                                                           @RequestHeader("Authorization") String jwt)
        throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);

        List<Rating> ratings = ratingService.getProductsRating(productId);

        return new ResponseEntity<>(ratings,HttpStatus.ACCEPTED);
    }

}
