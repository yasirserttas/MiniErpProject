package com.yasir.erp.minierp.modules.payment.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import java.math.BigDecimal;
import java.util.Set;

public interface PaymentAmountQueryPort {
    Set<Payment> findAllByAmountGreaterThanEqual(BigDecimal amount);
    Set<Payment> findAllByAmountLessThanEqual(BigDecimal amount);
    Set<Payment> findAllByAmountBetween(BigDecimal min, BigDecimal max);
}
