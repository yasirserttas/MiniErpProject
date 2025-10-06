package com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.stockMovement.application.dto.StockMovementDto;
import java.util.Set;

public interface ListStockMovementsByProductIdUseCase {
    Set<StockMovementDto> findAllDtoByProductId(String productId);
}