package com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemDto;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.request.UpdatePurchaseItemDtoRequest;

public interface UpdatePurchaseItemUseCase {
    PurchaseItemDto updatePurchaseItem(UpdatePurchaseItemDtoRequest request);
}