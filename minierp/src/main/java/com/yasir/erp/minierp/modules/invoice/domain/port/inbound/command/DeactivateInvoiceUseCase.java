package com.yasir.erp.minierp.modules.invoice.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;

public interface DeactivateInvoiceUseCase {
    InvoiceDto deactivateInvoice(String id);
}