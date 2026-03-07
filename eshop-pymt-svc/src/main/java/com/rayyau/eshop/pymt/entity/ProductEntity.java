package com.rayyau.eshop.pymt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_catalog")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_name", nullable = false, length = 50)
    private String name;

    @Column(nullable = false, precision = 13, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    @Column(length = 500)
    private String image;

    @Column(name = "on_sell", nullable = false)
    private Boolean isOnSell;
}