package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import java.util.Optional;

public interface PurchaseOrderQueryPort {
    Optional<PurchaseOrder> findByIdAndActive(String id, Boolean active);
}