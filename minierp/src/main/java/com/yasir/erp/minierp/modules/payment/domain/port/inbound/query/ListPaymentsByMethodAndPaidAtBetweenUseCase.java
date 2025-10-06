package com.yasir.erp.minierp.modules.payment.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import com.yasir.erp.minierp.modules.payment.domain.model.PaymentMethod;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListPaymentsByMethodAndPaidAtBetweenUseCase {
    Set<PaymentDto> findDtoAllByMethodAndPaidAtBetween
            (PaymentMethod method, LocalDateTime start, LocalDateTime end);
}