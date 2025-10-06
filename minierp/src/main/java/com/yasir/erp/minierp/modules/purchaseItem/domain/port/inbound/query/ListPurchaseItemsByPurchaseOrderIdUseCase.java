package com.yasir.erp.minierp.modules.purchaseItem.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemDto;
import java.util.Set;

public interface ListPurchaseItemsByPurchaseOrderIdUseCase {
    Set<PurchaseItemDto> findDtoAllByPurchaseOrderId(String purchaseOrderId);
}