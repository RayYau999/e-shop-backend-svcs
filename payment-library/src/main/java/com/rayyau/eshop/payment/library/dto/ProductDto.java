package com.rayyau.eshop.payment.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ProductDto implements Serializable {
    private static final long serialVersionUID = 2L;

    private Long productId;
    private Double price;
    private Long quantity;
}
