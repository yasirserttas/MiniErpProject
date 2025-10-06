package com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovementType;
import java.time.LocalDateTime;
import java.util.Set;

public interface StockMovementProductDateTypeQueryPort {
    Set<StockMovement> findAllByProductIdAndDateBetweenAndMovementType
            (String productId,
             LocalDateTime start,
             LocalDateTime end,
             StockMovementType movementType);
}