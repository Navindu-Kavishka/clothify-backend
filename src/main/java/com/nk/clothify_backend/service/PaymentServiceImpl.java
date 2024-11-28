package com.nk.clothify_backend.service;

import com.nk.clothify_backend.entity.OrderEntity;
import com.nk.clothify_backend.exception.OrderException;
import com.nk.clothify_backend.model.Order;
import com.nk.clothify_backend.repository.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Override
    public Session createStripeSession(Long orderId) throws StripeException, OrderException {
        Order order = orderService.findOrderById(orderId);

        return Session.create(SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/payment/" + orderId)
                .setCancelUrl("http://localhost:5173/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("lkr")
                                .setUnitAmount((long)(order.getTotalDiscountedPrice() * 100))
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Order #" + orderId)
                                        .build())
                                .build())
                        .setQuantity(1L)
                        .build())
                .build());
    }

    @Override
    public PaymentIntent retrievePayment(String paymentId) throws StripeException {
        return PaymentIntent.retrieve(paymentId);
    }

    @Override
    public void validatePayment(String paymentId, Long orderId) throws OrderException {
        try {
            Order order = orderService.findOrderById(orderId);
            PaymentIntent payment = retrievePayment(paymentId);

            if ("succeeded".equals(payment.getStatus())) {
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus("COMPLETED");
                order.setOrderStatus("PLACED");
                orderRepository.save(modelMapper.map(order, OrderEntity.class));
            }
        } catch (StripeException e) {
            throw new OrderException("Payment validation failed: " + e.getMessage());
        }
    }

    @Override
    public void handleSuccessfulPayment(PaymentIntent paymentIntent) throws OrderException {
        String orderId = paymentIntent.getMetadata().get("orderId");
        if (orderId != null) {
            Order order = orderService.findOrderById(Long.parseLong(orderId));
            order.getPaymentDetails().setStatus("COMPLETED");
            order.setOrderStatus("PLACED");
            orderRepository.save(modelMapper.map(order, OrderEntity.class));
        }
    }

    @Override
    public void handleFailedPayment(PaymentIntent paymentIntent) throws OrderException {
        String orderId = paymentIntent.getMetadata().get("orderId");
        if (orderId != null) {
            Order order = orderService.findOrderById(Long.parseLong(orderId));
            order.getPaymentDetails().setStatus("FAILED");
            order.setOrderStatus("PAYMENT_FAILED");
            orderRepository.save(modelMapper.map(order, OrderEntity.class));
        }
    }

}
