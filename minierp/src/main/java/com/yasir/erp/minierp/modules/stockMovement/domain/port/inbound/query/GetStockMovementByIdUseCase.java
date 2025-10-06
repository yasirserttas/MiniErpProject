package com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.stockMovement.application.dto.StockMovementDto;

public interface GetStockMovementByIdUseCase {
    StockMovementDto getStockMovementDtoById(String id);
}