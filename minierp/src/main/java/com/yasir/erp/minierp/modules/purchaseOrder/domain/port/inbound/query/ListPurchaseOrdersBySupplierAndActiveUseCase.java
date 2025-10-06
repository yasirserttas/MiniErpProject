package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import java.util.Set;

public interface ListPurchaseOrdersBySupplierAndActiveUseCase {
    Set<PurchaseOrderDto> listBySupplierAndActive(String supplierId, Boolean active);
}