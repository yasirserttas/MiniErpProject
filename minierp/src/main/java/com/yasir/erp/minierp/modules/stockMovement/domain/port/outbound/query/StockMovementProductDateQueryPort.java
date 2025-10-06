package com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import java.time.LocalDateTime;
import java.util.Set;

public interface StockMovementProductDateQueryPort {
    Set<StockMovement> findAllByProductIdAndDateBetween
            (String productId, LocalDateTime start, LocalDateTime end);
}