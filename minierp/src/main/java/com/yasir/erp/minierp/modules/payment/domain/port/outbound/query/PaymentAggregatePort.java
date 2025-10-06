package com.yasir.erp.minierp.modules.payment.domain.port.outbound.query;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentAggregatePort {
    BigDecimal sumPaidByCustomer(UUID customerId);
}
