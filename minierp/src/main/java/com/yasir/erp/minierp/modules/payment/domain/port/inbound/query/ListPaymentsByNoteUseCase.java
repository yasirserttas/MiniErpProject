package com.yasir.erp.minierp.modules.payment.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import java.util.Set;

public interface ListPaymentsByNoteUseCase {
    Set<PaymentDto> findDtoAllByNoteContainingIgnoreCase(String keyword);
}