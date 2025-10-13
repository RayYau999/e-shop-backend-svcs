package com.rayyau.eshop.pymt.service;

import com.rayyau.eshop.pymt.dto.PaymentEvent;
import com.rayyau.eshop.pymt.dto.PaymentStatusDto;
import com.rayyau.eshop.pymt.entity.PaymentStatusEntity;
import com.rayyau.eshop.pymt.mapper.PaymentStatusMapper;
import com.rayyau.eshop.pymt.repository.PaymentStatusRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentStatusRepository paymentStatusRepository;
    private final PaymentStatusMapper paymentStatusMapper;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public void savePaymentStatus(PaymentStatusDto paymentStatusDto) {
        PaymentStatusEntity paymentStatusEntity = paymentStatusMapper.paymentStatusDtoToPaymentStatusEntity(paymentStatusDto);
        paymentStatusRepository.save(paymentStatusEntity);
    }

    public void handlePaymentSuccess(PaymentEvent event) {
        kafkaTemplate.send("payment-events", event);
    }
}
