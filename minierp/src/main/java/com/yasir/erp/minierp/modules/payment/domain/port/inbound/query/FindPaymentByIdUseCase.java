package com.yasir.erp.minierp.modules.payment.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;

public interface FindPaymentByIdUseCase {
    PaymentDto findDtoById(String paymentId);
}