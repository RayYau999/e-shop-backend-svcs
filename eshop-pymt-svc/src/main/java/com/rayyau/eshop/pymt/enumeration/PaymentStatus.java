package com.rayyau.eshop.pymt.enumeration;

import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED"),
    CANCELLED("CANCELLED");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }
}
