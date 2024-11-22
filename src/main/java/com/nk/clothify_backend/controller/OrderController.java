package com.nk.clothify_backend.controller;

import com.nk.clothify_backend.exception.OrderException;
import com.nk.clothify_backend.exception.UserException;
import com.nk.clothify_backend.model.Address;
import com.nk.clothify_backend.model.Order;
import com.nk.clothify_backend.model.User;
import com.nk.clothify_backend.service.OrderService;
import com.nk.clothify_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
                                             @RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.createOrder(user,shippingAddress);

        log.info("order : "+order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization") String jwt)
            throws UserException{

        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<>(orders,HttpStatus.CREATED);
    }


    @GetMapping("/{Id}")
    public ResponseEntity<Order>findOrderById(@PathVariable("Id") Long orderId,
                                              @RequestHeader("Authorization") String jwt) throws UserException, OrderException{

        userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);

        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }

}
