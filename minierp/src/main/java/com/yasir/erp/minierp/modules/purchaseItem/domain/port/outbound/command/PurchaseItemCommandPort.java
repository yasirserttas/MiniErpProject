package com.yasir.erp.minierp.modules.purchaseItem.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;

public interface PurchaseItemCommandPort {
    PurchaseItem save(PurchaseItem purchaseItem);
    void delete(PurchaseItem purchaseItem);
}