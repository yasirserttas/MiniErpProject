package com.yasir.erp.minierp.modules.invoice.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import java.util.Set;

public interface ListInvoicesByIssuedByUseCase {
    Set<InvoiceDto> findDtoAllByIssuedByAndActive(String issuedBy, Boolean active);
}