package com.yasir.erp.minierp.modules.payment.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import java.util.Optional;

public interface PaymentQueryPort {
    Optional<Payment> findById(String id);
}
