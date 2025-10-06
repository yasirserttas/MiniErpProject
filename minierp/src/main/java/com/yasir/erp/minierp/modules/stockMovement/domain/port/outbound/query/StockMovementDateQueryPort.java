package com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import java.time.LocalDateTime;
import java.util.Set;

public interface StockMovementDateQueryPort {
    Set<StockMovement> findAllByDateBetween(LocalDateTime start, LocalDateTime end);
}