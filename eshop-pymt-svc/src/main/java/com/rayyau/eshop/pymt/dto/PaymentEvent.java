package com.rayyau.eshop.pymt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
public class PaymentEvent implements Serializable {
    private static final long serialVersionUID = 3L;

    private String paymentId;
    private String email;
    private String status;
    private Double amount;
    private String currency;
}
