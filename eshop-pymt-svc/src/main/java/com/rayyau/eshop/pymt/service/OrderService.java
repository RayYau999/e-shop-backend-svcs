package com.rayyau.eshop.pymt.service;

import com.rayyau.eshop.pymt.dto.Order;
import com.rayyau.eshop.pymt.mapper.OrderMapper;
import com.rayyau.eshop.pymt.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<Order> getOrderByIdAndUserId(Long orderId, Long userId) {

        return orderRepository.findAllByOrderIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("Orders not found"))
                .stream()
                .map(orderMapper::orderEntytyToOrder)
                .toList();
    }
}
