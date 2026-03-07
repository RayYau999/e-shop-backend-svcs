package com.rayyau.eshop.pymt.repository;

import com.rayyau.eshop.pymt.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByIsOnSell(boolean onSell);

    Optional<ProductEntity> findFirstById(Long productId);
}
