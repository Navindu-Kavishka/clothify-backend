package com.nk.clothify_backend.controller;

import com.nk.clothify_backend.exception.OrderException;
import com.nk.clothify_backend.model.Order;
import com.nk.clothify_backend.repository.OrderRepository;
import com.nk.clothify_backend.response.ApiResponse;
import com.nk.clothify_backend.response.PaymentLinkResponse;
import com.nk.clothify_backend.service.OrderService;
import com.nk.clothify_backend.service.PaymentService;
import com.nk.clothify_backend.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    @Value("${stripe.api.key}")
    String apiKey;

    private final PaymentService paymentService;
    private final OrderService orderService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String jwt) throws OrderException {

        Order order = orderService.findOrderById(orderId);

        try {
            Session session = paymentService.createStripeSession(orderId);

            PaymentLinkResponse res = new PaymentLinkResponse();
            res.setPayment_link_id(session.getId());
            res.setPayment_link_url(session.getUrl());

            return new ResponseEntity<>(res, HttpStatus.CREATED);

        } catch (StripeException e) {
            throw new OrderException(e.getMessage());
        }
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(
            @RequestParam(name = "payment_id") String paymentId,
            @RequestParam(name = "order_id") Long orderId) throws OrderException {

        try {
            paymentService.validatePayment(paymentId, orderId);

            ApiResponse res = new ApiResponse();
            res.setMessage("your order get placed...");
            res.setStatus(true);
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            throw new OrderException(e.getMessage());
        }
    }
}
