package com.yasir.erp.minierp.modules.payment.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import java.util.Set;

public interface ListPaymentsByInvoiceUseCase {
    Set<PaymentDto> findDtoAllByInvoiceId(String invoiceId);
}