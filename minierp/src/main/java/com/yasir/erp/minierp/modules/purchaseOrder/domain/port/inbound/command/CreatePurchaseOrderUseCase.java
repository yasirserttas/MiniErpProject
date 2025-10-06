package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.request.CreatePurchaseOrderDtoRequest;

public interface CreatePurchaseOrderUseCase {
    PurchaseOrderDto addPurchaseOrder(CreatePurchaseOrderDtoRequest req);
}