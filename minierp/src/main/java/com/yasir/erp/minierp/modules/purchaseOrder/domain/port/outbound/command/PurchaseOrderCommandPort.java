package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;

public interface PurchaseOrderCommandPort {
    PurchaseOrder save(PurchaseOrder po);
}