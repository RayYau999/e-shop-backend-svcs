package com.rayyau.eshop.pymt.enumeration;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    CONFIRMED("CONFIRMED");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
