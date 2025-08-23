package com.rayyau.eshop.login.controller;

import com.rayyau.eshop.login.client.OrderClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.rayyau.eshop.pymt.dto.Order;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private final OrderClient orderClient;

    @GetMapping("/user/{id}/orders")
    public List<Order> getUserOrders(@PathVariable("id") String id) {
        return orderClient.getOrdersByUserId(id);
    }
}
