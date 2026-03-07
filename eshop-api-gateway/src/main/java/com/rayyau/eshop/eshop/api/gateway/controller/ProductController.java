package com.rayyau.eshop.eshop.api.gateway.controller;

import com.rayyau.eshop.eshop.api.gateway.service.OrderService;
import com.rayyau.eshop.eshop.api.gateway.service.ProductService;
import com.rayyau.eshop.payment.library.annotation.UserId;
import com.rayyau.eshop.payment.library.dto.OrderDto;
import com.rayyau.eshop.payment.library.dto.ProductCatalogDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all-products-on-sell")
    public ResponseEntity<List<ProductCatalogDto>> getAllProductsOnSell() {
        log.info("getAllProducts running from product controller");
        return ResponseEntity.ok(productService.getAllProductsOnSell());
    }
}
