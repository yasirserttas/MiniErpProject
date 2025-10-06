package com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import java.util.Optional;
import java.util.Set;

public interface StockMovementQueryPort {
    Optional<StockMovement> findById(String id);
    Set<StockMovement> findAll();
}