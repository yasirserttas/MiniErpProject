package com.yasir.erp.minierp.modules.payment.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import java.time.LocalDateTime;
import java.util.Set;

public interface PaymentDateRangeQueryPort {
    Set<Payment> findAllByPaidAtBetween(LocalDateTime start, LocalDateTime end);
    Set<Payment> findAllByDueDateBetween(LocalDateTime start, LocalDateTime end);
    Set<Payment> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    Set<Payment> findAllByUpdatedAtBetween(LocalDateTime start, LocalDateTime end);
}
