package com.yasir.erp.minierp.modules.payment.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListPaymentsByCreatedAtBetweenUseCase {
    Set<PaymentDto> findDtoAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}