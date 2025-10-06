package com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovementType;
import java.time.LocalDateTime;
import java.util.Set;

public interface StockMovementTypeDateQueryPort {
    Set<StockMovement> findAllByMovementTypeAndDateBetween
            (StockMovementType movementType, LocalDateTime start, LocalDateTime end);
}