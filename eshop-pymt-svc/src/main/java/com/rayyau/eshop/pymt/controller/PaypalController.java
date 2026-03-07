package com.rayyau.eshop.pymt.controller;

import com.rayyau.eshop.payment.library.annotation.UserId;
import com.rayyau.eshop.payment.library.dto.OrderDto;
import com.rayyau.eshop.payment.library.dto.ProductDto;
import com.rayyau.eshop.pymt.dto.PaymentEvent;
import com.rayyau.eshop.pymt.dto.PaymentStatusDto;
import com.rayyau.eshop.pymt.entity.OrderEntity;
import com.rayyau.eshop.pymt.entity.PaymentStatusEntity;
import com.rayyau.eshop.pymt.enumeration.OrderStatus;
import com.rayyau.eshop.pymt.enumeration.PaymentStatus;
import com.rayyau.eshop.pymt.repository.OrderRepository;
import com.rayyau.eshop.pymt.repository.PaymentStatusRepository;
import com.rayyau.eshop.pymt.service.OrderService;
import com.rayyau.eshop.pymt.service.PaymentService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
@RequiredArgsConstructor
public class PaypalController {
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final EntityManager entityManager;

    // In-memory store (replace with persistent storage in production)
    private final Map<String, PaymentRecord> paymentStatuses = new ConcurrentHashMap<>();
    private final PaymentStatusRepository paymentStatusRepository;

    @Value("${spring.client.mail}")
    private String mailClient;

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
    @Transactional
    @GetMapping("/webhook/paypal/status/{paymentId}/{orderRefId}")
    public ResponseEntity<PaymentStatusEntity> getStatus(@PathVariable String paymentId, @PathVariable String orderRefId) {
        log.info("Retrieving status for payment ID2: {}", paymentId);
        //        PaymentRecord rec = paymentStatuses.get(paymentId);
        Optional<PaymentStatusEntity> rec = paymentStatusRepository.findByPaymentId(paymentId);
        try {
            if(rec.isPresent()) {
                if(rec.get().getStatus() == PaymentStatus.COMPLETED) {
                    // set the order status as paid in the order service
                    OrderEntity order = orderRepository.findByOrderRefId(orderRefId)
                            .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderRefId));
                    order.setOrderStatus(OrderStatus.CONFIRMED);
                    orderRepository.save(order);

                    // set payment_status table order_ref_id
                    PaymentStatusEntity paymentStatusEntity = rec.get();
                    paymentStatusEntity.setOrderRefId(orderRefId);
                    paymentStatusRepository.save(paymentStatusEntity);

                    //response ok with the payment record
                    log.info("Found payment record: {}", rec);
                    return ResponseEntity.ok(rec.get());
                }
                log.error("Found payment record but status is not completed: {}", rec);
                return ResponseEntity.internalServerError().build();
            } else {
                log.info("No record found for payment ID: {}", paymentId);
                return ResponseEntity.notFound().build();
            }
        } catch (NullPointerException e) {
            log.error("Order not found for orderRefId: {}", orderRefId);
            return ResponseEntity.notFound().build();
        }
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

    @GetMapping("/after-payment/{paymentId}")
    public ResponseEntity<String> afterPayment(@PathVariable String paymentId) {

        try {
            Optional<PaymentStatusEntity> rec = paymentStatusRepository.findByPaymentId(paymentId);
            if(rec.isPresent()) {
                log.info("afterPayment :: Found payment record: {}", rec);
                PaymentStatusEntity paymentStatusEntity = rec.get();
                if (paymentStatusEntity.getStatus().equals(PaymentStatus.COMPLETED)) {
                    PaymentEvent paymentEvent = PaymentEvent.builder()
                            .paymentId(paymentStatusEntity.getPaymentId())
                            .status(paymentStatusEntity.getStatus().name())
                            .email(mailClient) //replace this with real client email, TODO: get from the user details in db
                            .amount(paymentStatusEntity.getAmount())
                            .currency(paymentStatusEntity.getCurrency())
                            .build();
                    //send email event to kafka
                    paymentService.handlePaymentSuccess(paymentEvent);
                    return ResponseEntity.ok("Payment after payment handling success");
                }
                log.info("afterPayment :: Found payment record but status is not completed: {}", rec);
                return ResponseEntity.status(500).body("Found payment record but status is not completed");
            } else {
                log.info("No record found for payment ID: {}", paymentId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error inserting payment status: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error inserting payment status: " + e.getMessage());
        }
    }

}
