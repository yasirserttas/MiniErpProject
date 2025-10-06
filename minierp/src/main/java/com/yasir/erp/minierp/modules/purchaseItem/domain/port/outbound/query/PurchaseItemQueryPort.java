package com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;

import java.util.Optional;
import java.util.Set;

public interface PurchaseItemQueryPort {
    Optional<PurchaseItem> findById(String purchaseItemId);
    Set<PurchaseItem> findAllByPurchaseOrderId(String purchaseOrderId);
}