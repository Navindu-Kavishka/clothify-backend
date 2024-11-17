package com.nk.clothify_backend.controller;


import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.exception.UserException;
import com.nk.clothify_backend.model.Cart;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.request.AddItemRequest;
import com.nk.clothify_backend.service.CartService;
import com.nk.clothify_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
//@Tag()
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping("/")
    //@Operation
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

//    @PostMapping("/add")
//    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
//                                                     @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
//
//        User user = userService.findUserProfileByJwt(jwt);
//
//        cartService.addCartItem(user.getId(), req);
//
//        //ApiResponse res = new ApiResponse();
//    }



}
