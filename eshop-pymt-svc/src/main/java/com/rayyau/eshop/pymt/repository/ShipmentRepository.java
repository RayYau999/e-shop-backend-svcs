package com.rayyau.eshop.pymt.repository;

import com.rayyau.eshop.pymt.entity.OrderEntity;
import com.rayyau.eshop.pymt.entity.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<ShipmentEntity, Long> {
}
