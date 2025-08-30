package com.rayyau.eshop.pymt.repository;

import com.rayyau.eshop.pymt.dto.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<List<OrderEntity>> findAllByOrderIdAndUserId(Long orderId, Long userId);
}
