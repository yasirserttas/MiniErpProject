package com.yasir.erp.minierp.modules.invoice.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import java.util.Set;
import java.util.UUID;

public interface ListInvoicesByCustomerUseCase {
    Set<InvoiceDto> findDtoAllByCustomer_IdAndActiveIs(UUID customerId, Boolean active); }