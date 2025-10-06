package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus;
import java.util.Set;

public interface PurchaseOrderStatusSupplierActiveQueryPort {
    Set<PurchaseOrder> findAllByStatusAndSupplierIdAndActive(
            PurchaseOrderStatus status, String supplierId, Boolean active);
}