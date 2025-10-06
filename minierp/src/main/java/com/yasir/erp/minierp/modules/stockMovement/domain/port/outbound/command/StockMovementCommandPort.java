package com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;

public interface StockMovementCommandPort {
    StockMovement save(StockMovement stockMovement);
    void delete(StockMovement stockMovement);
}