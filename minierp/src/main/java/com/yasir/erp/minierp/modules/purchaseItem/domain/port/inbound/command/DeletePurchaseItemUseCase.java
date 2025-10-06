package com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.command;

public interface DeletePurchaseItemUseCase {
    void deleteById(String purchaseItemId);
}