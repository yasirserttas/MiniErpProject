package com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemDto;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.request.CreatePurchaseItemDtoRequest;

public interface CreatePurchaseItemUseCase {
    PurchaseItemDto createPurchaseItem(CreatePurchaseItemDtoRequest request);
}