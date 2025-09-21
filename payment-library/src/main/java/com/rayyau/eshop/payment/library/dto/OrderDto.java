package com.rayyau.eshop.payment.library.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Data
public class OrderDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NonNull
    private List<ProductDto> products;

    @NonNull
    private Double totalPrice;
}
