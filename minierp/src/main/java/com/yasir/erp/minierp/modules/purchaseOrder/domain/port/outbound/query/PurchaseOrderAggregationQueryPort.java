package com.yasir.erp.minierp.modules.purchaseOrder.domain.port.outbound.query;

import java.math.BigDecimal;

public interface PurchaseOrderAggregationQueryPort {
    BigDecimal sumTotalBySupplier(String supplierId);
}