package com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.stockMovement.application.dto.StockMovementDto;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovementType;
import java.util.Set;

public interface ListStockMovementsByMovementTypeUseCase {
    Set<StockMovementDto> findAllDtoByMovementType(StockMovementType movementType);
}