package com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface InvoiceCustomerQueryPort {
    Set<Invoice> findAllByCustomerIdAndActive(UUID customerId, Boolean active);
    Set<Invoice> findAllByCustomerIdAndCreatedAtBetweenAndActive
            (UUID customerId, LocalDateTime start, LocalDateTime end, Boolean active);
}
