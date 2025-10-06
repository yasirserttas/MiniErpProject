package com.yasir.erp.minierp.modules.stockMovement.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovementType;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.command.StockMovementCommandPort;
import com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.stockMovement.infrastructure.persistence.StockMovementJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public class StockMovementJpaAdapter implements
        StockMovementCommandPort,
        StockMovementQueryPort,
        StockMovementProductQueryPort,
        StockMovementTypeQueryPort,
        StockMovementDateQueryPort,
        StockMovementProductDateQueryPort,
        StockMovementProductTypeQueryPort,
        StockMovementTypeDateQueryPort,
        StockMovementProductDateTypeQueryPort {

    private final StockMovementJpaRepository stockMovementJpaRepository;

    public StockMovementJpaAdapter(StockMovementJpaRepository stockMovementJpaRepository) {
        this.stockMovementJpaRepository = stockMovementJpaRepository;
    }


    @Override
    public StockMovement save(StockMovement sm) {
        return stockMovementJpaRepository.save(sm);
    }

    @Override
    public void delete(StockMovement sm) {
        stockMovementJpaRepository.delete(sm);
    }

    @Override
    public Optional<StockMovement> findById(String id) {
        return stockMovementJpaRepository.findById(id);
    }

    @Override
    public Set<StockMovement> findAll() {
        return Set.copyOf(stockMovementJpaRepository.findAll());
    }

    @Override
    public Set<StockMovement> findAllByProductId(String productId) {
        return stockMovementJpaRepository.findAllByProduct_Id(productId);
    }

    @Override
    public Set<StockMovement> findAllByMovementType(StockMovementType movementType) {
        return stockMovementJpaRepository.findAllByMovementType(movementType);
    }

    @Override
    public Set<StockMovement> findAllByDateBetween(LocalDateTime start, LocalDateTime end) {
        return stockMovementJpaRepository.findAllByDateBetween(start, end);
    }

    @Override
    public Set<StockMovement> findAllByProductIdAndDateBetween
            (String productId, LocalDateTime start, LocalDateTime end) {
        return stockMovementJpaRepository.findAllByProduct_IdAndDateBetween(productId, start, end);
    }

    @Override
    public Set<StockMovement> findAllByProductIdAndMovementType
            (String productId, StockMovementType movementType) {
        return stockMovementJpaRepository.findAllByProduct_IdAndMovementType(productId, movementType);
    }

    @Override
    public Set<StockMovement> findAllByMovementTypeAndDateBetween
            (StockMovementType movementType, LocalDateTime start, LocalDateTime end) {
        return stockMovementJpaRepository.findAllByMovementTypeAndDateBetween(movementType, start, end);
    }

    @Override
    public Set<StockMovement> findAllByProductIdAndDateBetweenAndMovementType
            (String productId, LocalDateTime start, LocalDateTime end,
             StockMovementType movementType) {
        return stockMovementJpaRepository.findAllByProduct_IdAndDateBetweenAndMovementType
                (productId, start, end, movementType);
    }
}