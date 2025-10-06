package com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.stockMovement.application.dto.StockMovementDto;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovementType;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListStockMovementsByMovementTypeAndDateBetweenUseCase {
    Set<StockMovementDto> findAllDtoByMovementTypeAndDateBetween
            (StockMovementType movementType, LocalDateTime start, LocalDateTime end);
}