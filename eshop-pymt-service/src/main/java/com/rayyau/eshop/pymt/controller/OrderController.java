package com.rayyau.eshop.pymt.controller;

import com.rayyau.eshop.pymt.dto.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @GetMapping("/orders/{userId}")
    List<Order> getOrdersByUserId(@PathVariable("userId") String userId) {
        // Dummy implementation for demonstration purposes
        return List.of(
                new Order("1", userId, "APPLE", 1, "D"),
                new Order("2", userId, "ORANGE", 2, "A")
        );
    }
}
