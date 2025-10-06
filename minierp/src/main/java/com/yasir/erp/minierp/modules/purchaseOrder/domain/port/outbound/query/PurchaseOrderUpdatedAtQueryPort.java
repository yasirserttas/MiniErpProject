package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import java.time.LocalDateTime;
import java.util.Set;

public interface PurchaseOrderUpdatedAtQueryPort {
    Set<PurchaseOrder> findAllByUpdatedAtBetweenAndActive(LocalDateTime start, LocalDateTime end, Boolean active);
}