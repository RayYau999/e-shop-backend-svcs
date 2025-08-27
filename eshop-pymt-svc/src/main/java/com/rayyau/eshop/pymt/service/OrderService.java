package com.rayyau.eshop.pymt.service;

import com.rayyau.eshop.pymt.dto.Order;
import com.rayyau.eshop.pymt.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> getOrderByIdAndUserId(Long orderId, Long userId) {

        return orderRepository.findAllByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("Orders not found"))
                .stream()
                .map(orderEntity -> new Order(
                        orderEntity.getId().toString(),
                        orderEntity.getUserId().toString(),
                        orderEntity.getProductId().toString(),
                        orderEntity.getQuantity(),
                        orderEntity.getStatus()
                ))
                .toList();
    }
}
