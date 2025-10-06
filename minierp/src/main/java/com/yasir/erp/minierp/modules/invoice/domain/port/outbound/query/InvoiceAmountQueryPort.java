package com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;

import java.math.BigDecimal;
import java.util.Set;

public interface InvoiceAmountQueryPort {
    Set<Invoice> findAllByFinalAmountGreaterThanEqualAndActive
            (BigDecimal amount, Boolean active);
}
