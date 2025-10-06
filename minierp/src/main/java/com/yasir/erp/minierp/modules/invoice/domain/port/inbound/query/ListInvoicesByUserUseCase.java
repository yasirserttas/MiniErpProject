package com.yasir.erp.minierp.modules.invoice.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import java.util.Set;
import java.util.UUID;

public interface ListInvoicesByUserUseCase {
    Set<InvoiceDto> findDtoAllByUser_IdAndActive(UUID userId, Boolean active);
}