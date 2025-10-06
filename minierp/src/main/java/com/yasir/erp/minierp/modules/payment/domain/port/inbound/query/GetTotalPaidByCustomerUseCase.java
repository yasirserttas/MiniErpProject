package com.yasir.erp.minierp.modules.payment.domain.port.inbound.query;

import java.math.BigDecimal;
import java.util.UUID;

public interface GetTotalPaidByCustomerUseCase {
    BigDecimal getTotalPaidByCustomer(UUID customerId);
}