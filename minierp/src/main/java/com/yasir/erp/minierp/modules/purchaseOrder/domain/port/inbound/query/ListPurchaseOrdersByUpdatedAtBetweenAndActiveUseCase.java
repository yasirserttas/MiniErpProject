package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListPurchaseOrdersByUpdatedAtBetweenAndActiveUseCase {
    Set<PurchaseOrderDto> listByUpdatedAtBetweenAndActive(LocalDateTime start, LocalDateTime end, Boolean active);
}