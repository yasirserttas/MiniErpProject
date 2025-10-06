package com.yasir.erp.minierp.modules.invoice.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import com.yasir.erp.minierp.modules.invoice.application.dto.request.UpdateInvoiceDtoRequest;
public interface UpdateInvoiceUseCase {
    InvoiceDto updateInvoice(UpdateInvoiceDtoRequest req);
}