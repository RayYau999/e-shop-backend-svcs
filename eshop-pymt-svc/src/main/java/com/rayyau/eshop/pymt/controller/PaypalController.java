package com.rayyau.eshop.pymt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class PaypalController {

    private static final Logger log = LoggerFactory.getLogger(PaypalController.class);

    // In-memory store (replace with persistent storage in production)
    private final Map<String, PaymentRecord> paymentStatuses = new ConcurrentHashMap<>();

    // PayPal webhook endpoint
    @PostMapping("/webhook/paypal")
    public ResponseEntity<String> handlePaypalWebhook(@RequestBody PaypalWebhookEvent event) {
        String eventType = event.event_type();
        Map<String, Object> resource = event.resource();

        if ("PAYMENT.CAPTURE.COMPLETED".equals(eventType)) {
            Object idObj = resource.get("id");
            String paymentId = idObj != null ? idObj.toString() : "UNKNOWN";
            log.info("Webhook received: Payment Captured Successfully. ID: {}", paymentId);

            paymentStatuses.put(paymentId, new PaymentRecord("COMPLETED", resource));
            return ResponseEntity.ok("Payment recorded.");
        }

        log.info("Unhandled event type: {}", eventType);
        return ResponseEntity.ok("Event ignored.");
    }

    // Optional: retrieve stored payment status
    @GetMapping("/webhook/paypal/status/{paymentId}")
    public ResponseEntity<PaymentRecord> getStatus(@PathVariable String paymentId) {
        log.info("Retrieving status for payment ID2: {}", paymentId);
        PaymentRecord rec = paymentStatuses.get(paymentId);
        if(rec != null) {
            log.info("Found payment record: {}", rec);
            return ResponseEntity.ok(rec);
        } else {
            log.info("No record found for payment ID: {}", paymentId);
            return ResponseEntity.notFound().build();
        }
//        return rec != null ? ResponseEntity.ok(rec) : ResponseEntity.notFound().build();
    }

    // DTOs (records require Java 16+; convert to classes if using older Java)
    public record PaypalWebhookEvent(String event_type, Map<String, Object> resource) {}
    public record PaymentRecord(String status, Map<String, Object> details) {}
}
