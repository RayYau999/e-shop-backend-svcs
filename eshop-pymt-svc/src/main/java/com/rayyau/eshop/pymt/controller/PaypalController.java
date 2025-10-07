package com.rayyau.eshop.pymt.controller;

import com.rayyau.eshop.payment.library.annotation.UserId;
import com.rayyau.eshop.payment.library.dto.OrderDto;
import com.rayyau.eshop.pymt.dto.PaymentEvent;
import com.rayyau.eshop.pymt.dto.PaymentStatusDto;
import com.rayyau.eshop.pymt.entity.PaymentStatusEntity;
import com.rayyau.eshop.pymt.enumeration.PaymentStatus;
import com.rayyau.eshop.pymt.repository.PaymentStatusRepository;
import com.rayyau.eshop.pymt.service.OrderService;
import com.rayyau.eshop.pymt.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
public class PaypalController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    // In-memory store (replace with persistent storage in production)
    private final Map<String, PaymentRecord> paymentStatuses = new ConcurrentHashMap<>();
    private final PaymentStatusRepository paymentStatusRepository;

    // PayPal webhook endpoint
    @PostMapping("/webhook/paypal")
    public ResponseEntity<String> handlePaypalWebhook(@RequestBody PaypalWebhookEvent event) {
        String eventType = event.event_type();
        Map<String, Object> resource = event.resource();

        if ("PAYMENT.CAPTURE.COMPLETED".equals(eventType)) {
            Object idObj = resource.get("id");
            String paymentId = idObj != null ? idObj.toString() : "UNKNOWN";
            log.info("Webhook received: Payment Captured Successfully. ID: {}", paymentId);

            Double amount = null;
            String currency = null;
            Object amountObj = resource.get("amount");
            if (amountObj instanceof Map<?, ?> amountMap) {
                Object valueObj = amountMap.get("value");
                currency = amountMap.get("currency_code") != null ? amountMap.get("currency_code").toString() : null;
                if (valueObj != null) {
                    amount = Double.parseDouble(valueObj.toString());
                }
            }
            //            paymentStatuses.put(paymentId, new PaymentRecord("COMPLETED", resource));
            PaymentStatusDto paymentStatusDto = PaymentStatusDto.builder()
                    .paymentId(paymentId)
                    .status(PaymentStatus.COMPLETED)
                    .amount(amount)
                    .currency(currency)
                    .build();
            paymentService.savePaymentStatus(paymentStatusDto);
            return ResponseEntity.ok("Payment recorded.");
        }

        log.info("Unhandled event type: {}", eventType);
        return ResponseEntity.ok("Event ignored.");
    }

    // Optional: retrieve stored payment status
    @GetMapping("/webhook/paypal/status/{paymentId}")
    public ResponseEntity<PaymentStatusEntity> getStatus(@PathVariable String paymentId) {
        log.info("Retrieving status for payment ID2: {}", paymentId);
        //        PaymentRecord rec = paymentStatuses.get(paymentId);
        Optional<PaymentStatusEntity> rec = paymentStatusRepository.findByPaymentId(paymentId);
        if(rec.isPresent()) {
            log.info("Found payment record: {}", rec);
            return ResponseEntity.ok(rec.get());
        } else {
            log.info("No record found for payment ID: {}", paymentId);
            return ResponseEntity.notFound().build();
        }
        //        return rec != null ? ResponseEntity.ok(rec) : ResponseEntity.notFound().build();
    }

    // DTOs (records require Java 16+; convert to classes if using older Java)
    public record PaypalWebhookEvent(String event_type, Map<String, Object> resource) {}
    public record PaymentRecord(String status, Map<String, Object> details) {}

    @PostMapping("/insert-payment-status")
    public ResponseEntity<String> insertPaymentStatus(@RequestBody PaymentStatusDto paymentStatusDto) {
        log.info("Inserting payment status: {}", paymentStatusDto);
        try {
            paymentService.savePaymentStatus(paymentStatusDto);
            return ResponseEntity.ok("Payment status inserted.");
        } catch (Exception e) {
            log.error("Error inserting payment status: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error inserting payment status: " + e.getMessage());
        }
    }

    @PostMapping("kafka-test")
    public ResponseEntity<String> testKafka(@RequestBody PaymentEvent event) {
        try {
            paymentService.handlePaymentSuccess(event);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            log.error("Error handling payment success: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
