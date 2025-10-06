package com.yasir.erp.minierp.modules.receipt.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.receipt.application.dto.ReceiptDto;

public interface ActivateReceiptUseCase {
    ReceiptDto activateReceipt(String receiptId);
}