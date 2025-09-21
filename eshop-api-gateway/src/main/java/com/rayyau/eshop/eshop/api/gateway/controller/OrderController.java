package com.rayyau.eshop.eshop.api.gateway.controller;

import com.rayyau.eshop.eshop.api.gateway.service.OrderService;
import com.rayyau.eshop.payment.library.annotation.UserId;
import com.rayyau.eshop.payment.library.dto.OrderDto;
import com.rayyau.eshop.security.library.dto.LoginRequest;
import com.rayyau.eshop.security.library.dto.LoginResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place-orders")
    public ResponseEntity<String> placeOrder(@RequestBody @Valid OrderDto orderDto, @UserId Long userId) {
        log.info("order placing from api gateway controller");
        return ResponseEntity.ok(orderService.addOrder(orderDto, userId));
    }
}
