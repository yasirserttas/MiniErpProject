package com.yasir.erp.minierp.modules.receipt.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;

import java.time.LocalDateTime;
import java.util.Set;

public interface ListReceiptsByDateRangeUseCase {
    Set<ReceiptDto> findByDateRangeAndActiveStatus
            (LocalDateTime start, LocalDateTime end, boolean active);
}
