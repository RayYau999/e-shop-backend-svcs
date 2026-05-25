package com.rayyau.eshop.eshop.api.gateway.service;

import com.rayyau.eshop.payment.library.client.OrderClient;
import com.rayyau.eshop.payment.library.dto.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderClient orderClient;

    public String addOrder(OrderDto orderDto, Long userId) {
        return orderClient.addOrders(orderDto, userId);
    }

    public Map<String, String> setNonPaidOrders(OrderDto orderDto, Long userId) {
        return orderClient.setNonPaidOrders(orderDto, userId);
    }
}
