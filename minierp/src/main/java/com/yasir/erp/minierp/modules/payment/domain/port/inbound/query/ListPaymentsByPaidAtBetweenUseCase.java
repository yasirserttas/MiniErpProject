package com.yasir.erp.minierp.modules.payment.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListPaymentsByPaidAtBetweenUseCase {
    Set<PaymentDto> findDtoAllByPaidAtBetween(LocalDateTime start, LocalDateTime end);
}