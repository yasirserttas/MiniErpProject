package com.yasir.erp.minierp.modules.receipt.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;

public interface FindReceiptByIdUseCase {
    ReceiptDto findById(String id, boolean active);
}