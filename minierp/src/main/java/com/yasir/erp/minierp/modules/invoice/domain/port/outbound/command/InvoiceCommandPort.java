package com.yasir.erp.minierp.modules.invoice.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;

public interface InvoiceCommandPort {
    Invoice save(Invoice invoice);
    void delete(Invoice invoice);
}
