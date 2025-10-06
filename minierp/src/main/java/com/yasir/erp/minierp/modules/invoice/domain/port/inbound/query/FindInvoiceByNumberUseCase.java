package com.yasir.erp.minierp.modules.invoice.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;

public interface FindInvoiceByNumberUseCase {
    InvoiceDto findDtoByInvoiceNumberAndActive(String number, Boolean active);
}