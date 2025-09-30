package com.rayyau.eshop.pymt.repository;

import com.rayyau.eshop.pymt.entity.PaymentStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatusEntity, Long> {
    Optional<PaymentStatusEntity> findByPaymentId(String paymentId);
}
