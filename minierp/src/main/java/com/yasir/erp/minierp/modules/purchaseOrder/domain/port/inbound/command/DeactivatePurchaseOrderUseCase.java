package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;

public interface DeactivatePurchaseOrderUseCase {
    PurchaseOrderDto deactivatePurchaseOrder(String id);
}