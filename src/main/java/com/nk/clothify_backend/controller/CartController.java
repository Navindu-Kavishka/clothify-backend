package com.nk.clothify_backend.controller;


import com.nk.clothify_backend.exception.ProductException;
import com.nk.clothify_backend.exception.UserException;
import com.nk.clothify_backend.model.Cart;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.request.AddItemRequest;
import com.nk.clothify_backend.response.ApiResponse;
import com.nk.clothify_backend.service.CartService;
import com.nk.clothify_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping("/")
    //@Operation
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        log.info("Cart items in cart: " + cart.getCartItemEntities());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
                                                     @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);

        cartService.addCartItem(user.getId(), req);

        ApiResponse res = new ApiResponse();
        res.setMessage("item added to cart");
        res.setStatus(true);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }



}
