package com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.stockMovement.application.dto.StockMovementDto;
import com.yasir.erp.minierp.modules.stockMovement.application.dto.request.UpdateStockMovementDtoRequest;

public interface UpdateStockMovementUseCase {
    StockMovementDto updateStockMovement(UpdateStockMovementDtoRequest req);
}