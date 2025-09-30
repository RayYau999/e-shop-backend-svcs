package com.rayyau.eshop.pymt.entity;

import com.rayyau.eshop.pymt.enumeration.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "payment_status")
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class PaymentStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Double amount;

    private String currency;

}
