package com.rayyau.eshop.pymt.repository;

import com.rayyau.eshop.pymt.entity.PaymentStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatusEntity, Long> {
}
