package com.yasir.erp.minierp.modules.invoice.domain.port.inbound.query;
import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import java.time.LocalDateTime; import java.util.Set;
public interface ListInvoicesByCreatedAtUseCase {
    Set<InvoiceDto> findDtoAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, Boolean active);
}