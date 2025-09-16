package com.rayyau.eshop.pymt.service;

import com.rayyau.eshop.pymt.dto.Order;
import com.rayyau.eshop.pymt.dto.OrderDto;
import com.rayyau.eshop.pymt.entity.OrderEntity;
import com.rayyau.eshop.pymt.dto.ProductDto;
import com.rayyau.eshop.pymt.entity.ShipmentEntity;
import com.rayyau.eshop.pymt.enumeration.ShipmentStatus;
import com.rayyau.eshop.pymt.mapper.OrderMapper;
import com.rayyau.eshop.pymt.repository.OrderRepository;
import com.rayyau.eshop.pymt.repository.ShipmentRepository;
import com.rayyau.eshop.security.library.dto.UserEntity;
import com.rayyau.eshop.security.library.repository.UserRepository;
import com.rayyau.eshop.security.library.security.JwtUtil;
import jakarta.transaction.Transactional;
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
    private final ShipmentRepository shipmentRepository;
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

    @Transactional
    public void saveOrder(OrderDto orderDto, Long userId) {
        log.info("UserId extracted in add-orders: {}", userId);

//        OrderEntity orderEntity = orderMapper.orderDtoToOrderEntity(orderDto);

//        orderEntity.setUserId(1L);
//        orderRepository.save(orderEntity);

        OrderEntity orderEntity = orderMapper.orderDtoToOrderEntity(orderDto);
        orderEntity.setUserId(userId);
        orderRepository.saveAndFlush(orderEntity);
        //save shipment record to pending shipment queue
        List<ShipmentEntity> shipmentEntities = new ArrayList<>();
        for (ProductDto product : orderDto.getProducts()) {

            ShipmentEntity shipmentEntity = new ShipmentEntity();
            shipmentEntity.setOrder(orderEntity);
            shipmentEntity.setProductId(product.getProductId());
            shipmentEntity.setQuantity(product.getQuantity());
            shipmentEntity.setStatus(ShipmentStatus.PENDING);
            shipmentEntity.setPrice(product.getPrice());
            shipmentEntities.add(shipmentEntity);
        }
        log.info("shipmentEntities: {}", shipmentEntities);
        shipmentRepository.saveAll(shipmentEntities);

    }
}
