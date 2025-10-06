package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import java.util.Set;

public interface ListPurchaseOrdersByActiveUseCase {
    Set<PurchaseOrderDto> listByActive(Boolean active);
}