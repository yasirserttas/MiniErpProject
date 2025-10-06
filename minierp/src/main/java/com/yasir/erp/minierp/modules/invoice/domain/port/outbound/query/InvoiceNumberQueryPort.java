package com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;

import java.util.Optional;

public interface InvoiceNumberQueryPort {
    Optional<Invoice> findByInvoiceNumberAndActive(String number, Boolean active);
}
