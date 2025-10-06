package com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;

import java.util.Optional;
import java.util.Set;

public interface InvoiceActiveQueryPort {
    Optional<Invoice> findByIdAndActive(String id, Boolean active);
    Set<Invoice> findAllByActiveTrue();
    Set<Invoice> findAllByActiveFalse();
}
