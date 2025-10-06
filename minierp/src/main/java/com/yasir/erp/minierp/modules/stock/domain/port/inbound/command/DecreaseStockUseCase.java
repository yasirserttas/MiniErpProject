package com.yasir.erp.minierp.modules.stock.domain.port.inbound.command;

public interface DecreaseStockUseCase {
    void decreaseStock(String productId, int quantity);
}