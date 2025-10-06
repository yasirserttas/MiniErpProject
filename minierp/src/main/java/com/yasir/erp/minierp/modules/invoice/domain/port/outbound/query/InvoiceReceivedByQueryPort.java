package com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;

import java.util.Set;

public interface InvoiceReceivedByQueryPort {
    Set<Invoice> findAllByReceivedByAndActive(String receivedBy, Boolean active);
}