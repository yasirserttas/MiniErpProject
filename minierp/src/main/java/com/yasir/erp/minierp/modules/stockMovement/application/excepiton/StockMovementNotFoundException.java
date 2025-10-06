package com.yasir.erp.minierp.modules.stockMovement.application.excepiton;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class StockMovementNotFoundException extends BusinessException {

    public StockMovementNotFoundException(String stockMovementId) {
        super("STOCK_MOVEMENT_NOT_FOUND",
                String.format("StockMovement not found. ID[%s]",stockMovementId));
    }

}
