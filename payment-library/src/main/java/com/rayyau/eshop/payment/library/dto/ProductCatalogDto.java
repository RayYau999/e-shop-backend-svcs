package com.rayyau.eshop.payment.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // used for not including null values in the json response
public class ProductCatalogDto implements Serializable {
    private static final long serialVersionUID = 6L;

    @Nullable
    private Long id;

    private String name;
    private String description;
    private Double price;
    private String image;
    private Boolean isOnSell;
}
