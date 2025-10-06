package com.yasir.erp.minierp.modules.payment.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import java.math.BigDecimal;
import java.util.Set;

public interface ListPaymentsByAmountUseCase {
    Set<PaymentDto> findDtoAllByAmountGreaterThanEqual(BigDecimal amount);
    Set<PaymentDto> findDtoAllByAmountLessThanEqual(BigDecimal amount);
    Set<PaymentDto> findDtoAllByAmountBetween(BigDecimal min, BigDecimal max);
}