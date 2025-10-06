package com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovementType;
import java.util.Set;

public interface StockMovementProductTypeQueryPort {
    Set<StockMovement> findAllByProductIdAndMovementType
            (String productId, StockMovementType movementType);
}