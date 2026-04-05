package com.rayyau.eshop.eshop.api.gateway.service;

import com.rayyau.eshop.payment.library.client.OrderClient;
import com.rayyau.eshop.payment.library.client.ProductClient;
import com.rayyau.eshop.payment.library.dto.ProductCatalogDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductClient productClient;

    public List<ProductCatalogDto> getAllProductsOnSell() {
        log.info("running getAllProductsOnSell from product service");
        return productClient.getAllProductsOnSell();
    }

    public List<ProductCatalogDto> addProductsOnSell(List<ProductCatalogDto> products) throws RuntimeException {
        try {
            log.info("running updateProductsOnSell from product service");
            productClient.addProducts(products);
            return products;
        } catch (Exception e) {
            log.error("Error adding products on sell: {}", e.getMessage());
            throw new RuntimeException("Failed to add products on sell");
        }
    }
}
