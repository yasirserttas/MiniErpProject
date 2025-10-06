package com.yasir.erp.minierp.modules.invoice.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface ListInvoicesByCustomerAndDateUseCase {
    Set<InvoiceDto> findDtoAllByCustomer_IdAndCreatedAtBetweenAndActive
            (UUID customerId, LocalDateTime start, LocalDateTime end, Boolean active);
}