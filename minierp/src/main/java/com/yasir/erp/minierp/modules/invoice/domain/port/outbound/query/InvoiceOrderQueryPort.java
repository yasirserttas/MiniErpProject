package com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;

import java.util.Optional;
import java.util.Set;

public interface InvoiceOrderQueryPort {
    Optional<Invoice> findByOrderIdAndActive(String orderId, Boolean active);
    Set<Invoice> findAllByOrderIdAndActive(String orderId, Boolean active);
}
