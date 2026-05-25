package com.rayyau.eshop.payment.library.client;

import com.rayyau.eshop.payment.library.annotation.UserId;
import com.rayyau.eshop.payment.library.dto.OrderDto;
import com.rayyau.eshop.payment.library.dto.ProductCatalogDto;
import com.rayyau.eshop.security.library.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(configuration = FeignConfig.class, name = "eshop-pymt-svc", contextId = "eshopPymtOrderClient", url = "${eshop.pymt.service.url}")
public interface OrderClient {
    @PostMapping("/add-orders")
    String addOrders(@RequestBody OrderDto orderDto, @RequestParam Long userId);

    @PostMapping("/non-paid-orders")
    Map<String, String> setNonPaidOrders(@RequestBody OrderDto orderDto, @UserId Long userId);
}