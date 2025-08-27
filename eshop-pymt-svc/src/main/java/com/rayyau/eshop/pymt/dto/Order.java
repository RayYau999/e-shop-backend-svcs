package com.rayyau.eshop.pymt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Order {
    private String id;
    private String userId;
    private String productId;
    private int quantity;
    private String status;
}
