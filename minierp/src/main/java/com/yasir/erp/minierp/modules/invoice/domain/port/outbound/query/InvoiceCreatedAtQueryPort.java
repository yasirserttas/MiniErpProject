package com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;

import java.time.LocalDateTime;
import java.util.Set;

public interface InvoiceCreatedAtQueryPort {
    Set<Invoice> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, Boolean active);
}
