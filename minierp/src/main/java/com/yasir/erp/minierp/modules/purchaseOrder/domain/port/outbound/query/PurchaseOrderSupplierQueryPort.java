package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import java.util.Set;

public interface PurchaseOrderSupplierQueryPort {
    Set<PurchaseOrder> findAllBySupplierId(String supplierId);
}