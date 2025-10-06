package com.yasir.erp.minierp.modules.payment.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import java.util.Set;

public interface PaymentInvoiceQueryPort {
    Set<Payment> findAllByInvoiceId(String invoiceId);
}
