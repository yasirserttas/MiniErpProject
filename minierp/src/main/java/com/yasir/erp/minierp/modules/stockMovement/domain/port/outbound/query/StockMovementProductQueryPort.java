package com.yasir.erp.minierp.modules.stockMovement.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovement;
import java.util.Set;

public interface StockMovementProductQueryPort {
    Set<StockMovement> findAllByProductId(String productId);
}