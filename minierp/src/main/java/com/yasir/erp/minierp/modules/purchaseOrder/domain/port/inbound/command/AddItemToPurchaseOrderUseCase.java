package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.request.CreatePurchaseItemDtoRequest;

public interface AddItemToPurchaseOrderUseCase {
    PurchaseOrderDto addItemToPurchaseOrder(String orderId, CreatePurchaseItemDtoRequest req);
}