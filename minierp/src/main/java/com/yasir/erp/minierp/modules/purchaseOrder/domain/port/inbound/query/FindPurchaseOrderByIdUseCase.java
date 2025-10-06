package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;

public interface FindPurchaseOrderByIdUseCase {
    PurchaseOrderDto findById(String id, Boolean active);
}