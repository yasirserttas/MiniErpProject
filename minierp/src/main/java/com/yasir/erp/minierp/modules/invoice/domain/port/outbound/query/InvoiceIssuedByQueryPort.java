package com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;

import java.util.Set;

public interface InvoiceIssuedByQueryPort {
    Set<Invoice> findAllByIssuedByAndActive(String issuedBy, Boolean active);
}
