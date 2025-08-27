package com.rayyau.eshop.pymt.controller;

import com.rayyau.eshop.pymt.dto.Order;
import com.rayyau.eshop.pymt.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders/{orderId}/{userId}")
    List<Order> getOrdersByUserId(@PathVariable("orderId") Long orderId, @PathVariable("userId") Long userId) {
        // Dummy implementation for demonstration purposes
        return orderService.getOrderByIdAndUserId(orderId, userId);
    }

    @GetMapping("/orders/testing")
    String testing() {
        log.info("you can access this without login");
        // Dummy implementation for demonstration purposes
        return "you can access this without login";
    }
}
