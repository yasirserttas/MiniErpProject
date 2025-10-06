package com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;

import java.util.Set;
import java.util.UUID;

public interface InvoiceUserQueryPort {
    Set<Invoice> findAllByUserIdAndActive(UUID userId, Boolean active);
}
