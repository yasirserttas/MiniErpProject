package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command;

public interface RecalculatePurchaseOrderTotalUseCase {
    void recalculateTotalAmount(String orderId);
}