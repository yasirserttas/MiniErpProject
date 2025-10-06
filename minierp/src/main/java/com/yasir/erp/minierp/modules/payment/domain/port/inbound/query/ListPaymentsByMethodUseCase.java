package com.yasir.erp.minierp.modules.payment.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import com.yasir.erp.minierp.modules.payment.domain.model.PaymentMethod;
import java.util.Set;

public interface ListPaymentsByMethodUseCase {
    Set<PaymentDto> findDtoAllByMethod(PaymentMethod method);
}