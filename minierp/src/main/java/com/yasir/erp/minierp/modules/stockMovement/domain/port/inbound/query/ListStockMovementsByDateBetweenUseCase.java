package com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.stockMovement.application.dto.StockMovementDto;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListStockMovementsByDateBetweenUseCase {
    Set<StockMovementDto> findAllDtoByDateBetween(LocalDateTime start, LocalDateTime end);
}