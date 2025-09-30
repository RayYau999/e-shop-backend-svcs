package com.rayyau.eshop.pymt.dto;

import com.rayyau.eshop.pymt.enumeration.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PaymentStatusDto {
    private String paymentId;

    private PaymentStatus status;

    private Double amount;

    private String currency;
}
