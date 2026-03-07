package com.rayyau.eshop.eshop.api.gateway.service;

import com.rayyau.eshop.payment.library.client.OrderClient;
import com.rayyau.eshop.payment.library.dto.ProductCatalogDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private OrderClient orderClient;

    public List<ProductCatalogDto> getAllProductsOnSell() {
        return orderClient.getAllProductsOnSell();
    }
}
