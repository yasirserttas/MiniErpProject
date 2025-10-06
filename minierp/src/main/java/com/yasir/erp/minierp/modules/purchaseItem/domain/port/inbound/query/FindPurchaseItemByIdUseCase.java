package com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemDto;

public interface FindPurchaseItemByIdUseCase {
    PurchaseItemDto findDtoByPurchaseItemId(String purchaseItemId);
}