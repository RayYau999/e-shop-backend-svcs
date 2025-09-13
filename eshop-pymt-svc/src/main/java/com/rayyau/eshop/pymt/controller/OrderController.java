package com.rayyau.eshop.pymt.controller;

import com.rayyau.eshop.pymt.annotation.UserId;
import com.rayyau.eshop.pymt.dto.Order;
import com.rayyau.eshop.pymt.dto.OrderDto;
import com.rayyau.eshop.pymt.dto.OrderReqDto;
import com.rayyau.eshop.pymt.service.OrderService;
import com.rayyau.eshop.security.library.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final JwtUtil jwtUtils;

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

    @PostMapping("/add-orders")
    void addOrders(@RequestBody OrderDto orderDto, @UserId Long userId) {
        //should consider to make the header as an annotation to input the userId directly
        orderService.saveOrder(orderDto, userId);
    }
}
