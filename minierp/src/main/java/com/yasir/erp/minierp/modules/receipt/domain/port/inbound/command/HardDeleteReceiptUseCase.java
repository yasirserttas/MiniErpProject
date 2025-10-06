package com.yasir.erp.minierp.modules.receipt.domain.port.inbound.command;

public interface HardDeleteReceiptUseCase {
    void hardDeleteReceipt(String receiptId);
}