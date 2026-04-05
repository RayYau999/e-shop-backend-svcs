package com.rayyau.eshop.eshop.api.gateway.controller;

import com.rayyau.eshop.eshop.api.gateway.service.OrderService;
import com.rayyau.eshop.eshop.api.gateway.service.ProductService;
import com.rayyau.eshop.payment.library.annotation.UserId;
import com.rayyau.eshop.payment.library.dto.OrderDto;
import com.rayyau.eshop.payment.library.dto.ProductCatalogDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "products", key = "'all_on_sell'")
    @GetMapping("/all-products-on-sell")
    public ResponseEntity<List<ProductCatalogDto>> getAllProductsOnSell() {
        log.info("getAllProducts running from product controller");
        return ResponseEntity.ok(productService.getAllProductsOnSell());
    }

    @PostMapping("/all-products-on-sell")
    @CacheEvict(value = "products", key = "'all_on_sell'")
    //@CachePut(value = "products", key = "'all_on_sell'") will replace the whole cache without the old records,
    // so we should use @CacheEvict to clear the cache and let the next getAllProductsOnSell to repopulate the cache
    // with the new records
    public ResponseEntity<List<ProductCatalogDto>> addProductsOnSell(@RequestBody List<ProductCatalogDto> products) {
        try {
            log.info("updateProductsOnSell running from product controller");
            productService.addProductsOnSell(products);
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            log.error("Internal error while adding products on sell: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}
