package com.yasir.erp.minierp.modules.receipt.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;

import java.time.LocalDateTime;
import java.util.Set;

public interface ListReceiptsByIssuedByAndDateUseCase {
    Set<ReceiptDto> findAllByIssuerAndDateRangeAndStatus
            (String issuedBy, LocalDateTime start, LocalDateTime end, boolean active);
}
