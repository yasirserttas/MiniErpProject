package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus;
import java.time.LocalDateTime;
import java.util.Set;

public interface PurchaseOrderStatusCreatedAtQueryPort {
    Set<PurchaseOrder> findAllByStatusAndCreatedAtBetweenAndActive(
            PurchaseOrderStatus status, LocalDateTime start, LocalDateTime end, Boolean active);
}