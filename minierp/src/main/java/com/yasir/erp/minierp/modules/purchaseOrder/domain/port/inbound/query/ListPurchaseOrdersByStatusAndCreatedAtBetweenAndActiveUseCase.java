package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListPurchaseOrdersByStatusAndCreatedAtBetweenAndActiveUseCase {
    Set<PurchaseOrderDto> listByStatusAndCreatedAtBetweenAndActive(
            PurchaseOrderStatus status, LocalDateTime start, LocalDateTime end, Boolean active);
}