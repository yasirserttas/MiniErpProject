package com.yasir.erp.minierp.modules.payment.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.payment.domain.model.Payment;

public interface PaymentCommandPort {
    Payment save(Payment payment);
    void delete(Payment payment);
}
