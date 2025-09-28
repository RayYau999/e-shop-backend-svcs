package com.rayyau.eshop.pymt.service;

import com.rayyau.eshop.pymt.dto.PaymentStatusDto;
import com.rayyau.eshop.pymt.entity.PaymentStatusEntity;
import com.rayyau.eshop.pymt.mapper.PaymentStatusMapper;
import com.rayyau.eshop.pymt.repository.PaymentStatusRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentStatusRepository paymentStatusRepository;
    private final PaymentStatusMapper paymentStatusMapper;

    public void savePaymentStatus(PaymentStatusDto paymentStatusDto) {
        PaymentStatusEntity paymentStatusEntity = paymentStatusMapper.paymentStatusDtoToPaymentStatusEntity(paymentStatusDto);
        paymentStatusRepository.save(paymentStatusEntity);
    }
}
