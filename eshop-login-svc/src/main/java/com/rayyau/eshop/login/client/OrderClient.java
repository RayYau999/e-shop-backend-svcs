package com.rayyau.eshop.login.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.rayyau.eshop.pymt.dto.Order;

import java.util.List;

@FeignClient(name = "eshop-pymt-svc", url = "${eshop.pymt.service.url}")
public interface OrderClient {
    @GetMapping("/orders/{userId}")
    List<Order> getOrdersByUserId(@PathVariable("userId") String userId);
}
