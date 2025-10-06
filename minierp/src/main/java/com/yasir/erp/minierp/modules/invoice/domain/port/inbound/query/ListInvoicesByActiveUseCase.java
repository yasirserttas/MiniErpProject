package com.yasir.erp.minierp.modules.invoice.domain.port.inbound.query;
import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import java.util.Set;
public interface ListInvoicesByActiveUseCase {
    Set<InvoiceDto> findDtoAllByActiveTrue();
    Set<InvoiceDto> findDtoAllByActiveFalse();
}