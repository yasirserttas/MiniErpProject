package com.yasir.erp.minierp.modules.stock.domain.port.inbound.command;

public interface IncreaseStockUseCase {
    void increaseStock(String productId, int quantity);
}