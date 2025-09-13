package com.rayyau.eshop.pymt.service;

import com.rayyau.eshop.pymt.dto.Order;
import com.rayyau.eshop.pymt.dto.OrderDto;
import com.rayyau.eshop.pymt.entity.OrderEntity;
import com.rayyau.eshop.pymt.dto.ProductDto;
import com.rayyau.eshop.pymt.mapper.OrderMapper;
import com.rayyau.eshop.pymt.repository.OrderRepository;
import com.rayyau.eshop.security.library.dto.UserEntity;
import com.rayyau.eshop.security.library.repository.UserRepository;
import com.rayyau.eshop.security.library.security.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public List<Order> getOrderByIdAndUserId(Long orderId, Long userId) {

//        return orderRepository.findAllByOrderIdAndUserId(orderId, userId)
//                .orElseThrow(() -> new RuntimeException("Orders not found"))
//                .stream()
//                .map(orderMapper::orderEntityToOrder)
//                .toList();
        return new ArrayList<>();
    }

    public void saveOrder(OrderDto orderDto, Long userId) {
//        String userName = jwtUtil.extractUsername(token);
//        log.info("Authenticated userName extracted in add-orders: {}", userName);
//
//        UserEntity userEntity = userRepository.findByUsername(userName)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Long userId = userEntity.getId();
//        Long userId = token;
        log.info("UserId extracted in add-orders: {}", userId);

        OrderEntity orderEntity = orderMapper.orderDtoToOrderEntity(orderDto);

        orderEntity.setUserId(1L);
        orderRepository.save(orderEntity);
    }
}
