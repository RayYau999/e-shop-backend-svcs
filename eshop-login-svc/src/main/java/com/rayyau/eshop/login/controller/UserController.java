package com.rayyau.eshop.login.controller;

import com.rayyau.eshop.payment.library.annotation.UserId;
import com.rayyau.eshop.payment.library.client.OrderClient;
import com.rayyau.eshop.payment.library.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {
    private final OrderClient orderClient;

    @PostMapping("/add-order-login")
    ResponseEntity<String> addOrders(@RequestBody OrderDto orderDto, @UserId Long userId) {
        log.info("running addOrders in UserController with userId: {}", userId);
        String result = orderClient.addOrders(orderDto, userId);
        return ResponseEntity.ok(result);
    }
}
