package com.nk.clothify_backend.service;

import com.nk.clothify_backend.exception.OrderException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;


public interface PaymentService {
    Session createStripeSession(Long orderId) throws StripeException, OrderException;
    PaymentIntent retrievePayment(String paymentId) throws StripeException;
    void validatePayment(String paymentId, Long orderId) throws OrderException;
    void handleSuccessfulPayment(PaymentIntent paymentIntent) throws OrderException;
    void handleFailedPayment(PaymentIntent paymentIntent) throws OrderException;
}
