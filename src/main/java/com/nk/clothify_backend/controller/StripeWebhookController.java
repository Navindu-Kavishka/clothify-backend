package com.nk.clothify_backend.controller;

import com.nk.clothify_backend.service.PaymentService;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
public class StripeWebhookController {

    @Value("${stripe.api.webhook.secret}")
    private String endpointSecret;

    private final PaymentService paymentService;

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            switch (event.getType()) {
                case "payment_intent.succeeded":
                    PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().get();
                    paymentService.handleSuccessfulPayment(paymentIntent);
                    break;

                case "payment_intent.payment_failed":
                    paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().get();
                    paymentService.handleFailedPayment(paymentIntent);
                    break;
            }

            return new ResponseEntity<>("Webhook processed", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Webhook error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
