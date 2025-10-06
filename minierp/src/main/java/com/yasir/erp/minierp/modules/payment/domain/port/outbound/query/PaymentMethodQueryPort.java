package com.yasir.erp.minierp.modules.payment.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import com.yasir.erp.minierp.modules.payment.domain.model.PaymentMethod;
import java.time.LocalDateTime;
import java.util.Set;

public interface PaymentMethodQueryPort {
    Set<Payment> findAllByMethod(PaymentMethod method);
    Set<Payment> findAllByInvoiceIdAndMethod(String invoiceId, PaymentMethod method);
    Set<Payment> findAllByMethodAndPaidAtBetween(PaymentMethod method, LocalDateTime start, LocalDateTime end);
}
