package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus;
import java.util.Set;

public interface PurchaseOrderStatusQueryPort {
    Set<PurchaseOrder> findAllByStatus(com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus status);
}