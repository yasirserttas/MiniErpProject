package com.yasir.erp.minierp.modules.payment.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import com.yasir.erp.minierp.modules.payment.application.dto.request.UpdatePaymentDtoRequest;

public interface UpdatePaymentUseCase {
    PaymentDto updatePayment(UpdatePaymentDtoRequest req);
}