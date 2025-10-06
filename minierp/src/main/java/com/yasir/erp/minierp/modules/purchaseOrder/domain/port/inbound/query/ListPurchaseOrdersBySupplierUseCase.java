package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import java.util.Set;

public interface ListPurchaseOrdersBySupplierUseCase {
    Set<PurchaseOrderDto> listBySupplier(String supplierId);
}