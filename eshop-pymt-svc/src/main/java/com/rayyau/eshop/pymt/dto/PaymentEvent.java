package com.rayyau.eshop.pymt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Setter
@Getter
public class PaymentEvent implements Serializable {
    private static final long serialVersionUID = 3L;

    private String orderId;
    private String email;
    private String status;
}
