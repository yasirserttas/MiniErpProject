package com.yasir.erp.minierp.modules.stock.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class StockNotFoundException extends BusinessException {

    public StockNotFoundException(String stockId) {
        super("STOCK_NOT_FOUND",
                String.format("Stock not found. ID[%s]",stockId));
    }
}
