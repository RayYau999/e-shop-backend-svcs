package com.rayyau.eshop.payment.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ProductCatalogDto implements Serializable {
    private static final long serialVersionUID = 6L;

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;
    private Boolean isOnSell;
}
