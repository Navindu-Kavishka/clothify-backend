package com.nk.clothify_backend.controller;

import com.nk.clothify_backend.entity.OrderEntity;
import com.nk.clothify_backend.exception.OrderException;
import com.nk.clothify_backend.model.Order;
import com.nk.clothify_backend.repository.OrderRepository;
import com.nk.clothify_backend.response.ApiResponse;
import com.nk.clothify_backend.response.PaymentLinkResponse;
import com.nk.clothify_backend.service.OrderService;
import com.nk.clothify_backend.service.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    @Value("{razorpay.api.key}")
    String apiKey;

    @Value("{razorpay.api.secret}")
    String apiSecret;

    private final OrderService orderService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException, RazorpayException {

        Order order = orderService.findOrderById(orderId);

        try {
            RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();

            paymentLinkRequest.put("amount",order.getTotalPrice()*100);
            paymentLinkRequest.put("currency","LKR");

            JSONObject customer = new JSONObject();
            customer.put("name",order.getUserEntity().getFirstName());
            customer.put("email",order.getUserEntity().getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify = new JSONObject();
            notify.put("sms",true);
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("callback_url","http://localhost:5173/payment/"+orderId);
            paymentLinkRequest.put("callback_method","get");

            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse res = new PaymentLinkResponse();
            res.setPayment_link_id(paymentLinkId);
            res.setPayment_link_url(paymentLinkUrl);

            return new ResponseEntity<>(res, HttpStatus.CREATED);

        } catch (Exception e) {
            //handle
            throw new RazorpayException(e.getMessage());
        }

    }

    public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId, @RequestParam(name = "order_id") Long orderId) throws OrderException, RazorpayException {

        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecret);

        try {
            Payment payment = razorpay.payments.fetch(paymentId);

            if (payment.get("status").equals("captured")) {
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus("COMPLETED");
                order.setOrderStatus("PLACED");
                orderRepository.save(modelMapper.map(order, OrderEntity.class));

            }

            ApiResponse res = new ApiResponse();
            res.setMessage("your order get placed...");
            res.setStatus(true);
            return new ResponseEntity<>(res,HttpStatus.ACCEPTED);

        } catch (Exception e) {
            //handle
            throw new RazorpayException(e.getMessage());
        }
    }

}
