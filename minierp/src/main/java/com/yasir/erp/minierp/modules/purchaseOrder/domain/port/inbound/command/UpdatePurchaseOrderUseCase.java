package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.request.UpdatePurchaseOrderDtoRequest;

public interface UpdatePurchaseOrderUseCase {
    PurchaseOrderDto updatePurchaseOrder(UpdatePurchaseOrderDtoRequest req);
}