package com.yasir.erp.minierp.modules.invoice.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;

import java.math.BigDecimal;
import java.util.Set;

public interface ListInvoicesByAmountMinUseCase {
    Set<InvoiceDto> findDtoAllByFinalAmountGreaterThanEqualAndActive
            (BigDecimal amount, Boolean active);
}
