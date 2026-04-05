package com.rayyau.eshop.payment.library.client;

import com.rayyau.eshop.payment.library.dto.ProductCatalogDto;
import com.rayyau.eshop.security.library.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(configuration = FeignConfig.class, name = "eshop-pymt-svc", contextId = "eshopPymtProductClient", url = "${eshop.pymt.service.url}")
public interface ProductClient {
    @GetMapping("/product/all-products-on-sell")
    List<ProductCatalogDto> getAllProductsOnSell();

    @PostMapping("/product/all-products-on-sell")
    List<ProductCatalogDto> addProducts(@RequestBody List<ProductCatalogDto> products) throws RuntimeException ;
}
