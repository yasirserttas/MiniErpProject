package com.yasir.erp.minierp.modules.receipt.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;

import java.util.Set;

public interface ListReceiptsUseCase {
    Set<ReceiptDto> findAll();
}
