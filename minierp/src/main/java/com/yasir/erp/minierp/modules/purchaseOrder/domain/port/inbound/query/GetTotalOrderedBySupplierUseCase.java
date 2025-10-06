package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.inbound.query;

import java.math.BigDecimal;

public interface GetTotalOrderedBySupplierUseCase {
    BigDecimal getTotalOrderedBySupplier(String supplierId);
}