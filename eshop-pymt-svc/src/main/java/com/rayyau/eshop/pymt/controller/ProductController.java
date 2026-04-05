package com.rayyau.eshop.pymt.controller;

import com.rayyau.eshop.payment.library.dto.ProductCatalogDto;
import com.rayyau.eshop.pymt.entity.ProductEntity;
import com.rayyau.eshop.pymt.mapper.ProductMapper;
import com.rayyau.eshop.pymt.service.ProductService;
import com.sun.jdi.InternalException;
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
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/all-products-on-sell")
    public List<ProductCatalogDto> getAllProductsOnSell() {
        log.info("getAllProducts running from product controller");
        List<ProductEntity> products = productService.getAllProductsOnSell();
        List<ProductCatalogDto> productCatalogDtos = products.stream().map(productMapper::productEntityToProductCatalogDto).toList();

        if(productCatalogDtos.isEmpty()) {
            throw new IllegalArgumentException("No products found on sell");
        }
//        return ResponseEntity.ok(productCatalogDtos);
        return productCatalogDtos;
    }

    @PostMapping("/all-products-on-sell")
    public List<ProductCatalogDto> addProductsOnSell(@RequestBody List<ProductCatalogDto> products) throws RuntimeException {
        try {
            log.info("addProductsOnSell running from product controller");
            List<ProductEntity> productEntities = products.stream().map(productMapper::productCatalogDtoToProductEntity).toList();
            productService.addProductsOnSell(productEntities);
            return products;
        } catch (Exception e) {
            log.error("Error adding products on sell: {}", e.getMessage());
            throw new RuntimeException("Failed to add products on sell");
        }
    }
}

