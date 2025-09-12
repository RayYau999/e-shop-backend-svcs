package com.rayyau.eshop.pymt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class Order {
    private String id;
    private Long orderId;
    private Long userId;
    private String productId;
    private Double price;
    private int quantity;
    private String status;
    private LocalDateTime createdAt;
}
