package com.yasir.erp.minierp.modules.stockMovement.infrastructure.persistence;

import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface StockMovementJpaRepository extends JpaRepository<StockMovement, String> {

    Set<StockMovement> findAllByProduct_Id(String productId);

    Set<StockMovement> findAllByMovementType(StockMovementType movementType);

    Set<StockMovement> findAllByDateBetween(LocalDateTime start, LocalDateTime end);

    Set<StockMovement> findAllByProduct_IdAndDateBetween(
            String productId, LocalDateTime start, LocalDateTime end);

    Set<StockMovement> findAllByProduct_IdAndMovementType(
            String productId, StockMovementType movementType);

    Set<StockMovement> findAllByMovementTypeAndDateBetween(
            StockMovementType movementType, LocalDateTime start, LocalDateTime end);

    Set<StockMovement> findAllByProduct_IdAndDateBetweenAndMovementType(
            String productId, LocalDateTime start, LocalDateTime end, StockMovementType movementType);
}
