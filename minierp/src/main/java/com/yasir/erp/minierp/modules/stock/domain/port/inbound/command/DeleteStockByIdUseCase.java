package com.yasir.erp.minierp.modules.stock.domain.port.inbound.command;

public interface DeleteStockByIdUseCase {
    void deleteStockById(String stockId);
}