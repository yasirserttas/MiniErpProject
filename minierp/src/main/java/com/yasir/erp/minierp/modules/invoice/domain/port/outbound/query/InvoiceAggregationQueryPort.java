package com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query;

import java.math.BigDecimal;
import java.util.UUID;

public interface InvoiceAggregationQueryPort {
    BigDecimal sumTotalByCustomer(UUID customerId);
}
