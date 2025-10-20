package com.rayyau.eshop.pymt.service;

import com.rayyau.eshop.pymt.dto.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendPaymentSuccessEmail(PaymentEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.getEmail());
        message.setSubject("Payment Successful");

        String text = """
        Dear Customer,

        We are pleased to inform you that your payment with ID %s for the amount of %.2f %s has been successfully processed.

        Thank you for shopping with us!

        Best regards,
        E-Shop Team
        """.formatted(event.getPaymentId(), event.getAmount(), event.getCurrency());

        message.setText(text);
        mailSender.send(message);
    }
}
