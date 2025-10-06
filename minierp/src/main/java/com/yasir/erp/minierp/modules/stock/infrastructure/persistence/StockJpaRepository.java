package com.yasir.erp.minierp.modules.stock.infrastructure.persistence;

import com.yasir.erp.minierp.modules.stock.domain.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockJpaRepository extends JpaRepository<Stock, String> {
    Optional<Stock> findByProductId(String productId);
}