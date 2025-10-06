package com.yasir.erp.minierp.modules.stockMovement.domain.port.inbound.command;

public interface DeleteStockMovementByIdUseCase {
    void deleteStockMovementById(String id);
}