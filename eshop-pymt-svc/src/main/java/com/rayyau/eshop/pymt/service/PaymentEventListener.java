package com.rayyau.eshop.pymt.service;

import com.rayyau.eshop.pymt.dto.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentEventListener {

    private final EmailService emailService;

    public PaymentEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "payment-events", groupId = "email-service")
    public void listen(PaymentEvent event) {
        if ("COMPLETED".equals(event.getStatus())) {
            log.info("add to email queue: Payment successful for paymentId: {}, email: {}", event.getPaymentId(), event.getEmail());
            emailService.sendPaymentSuccessEmail(event);
        }
    }
}
