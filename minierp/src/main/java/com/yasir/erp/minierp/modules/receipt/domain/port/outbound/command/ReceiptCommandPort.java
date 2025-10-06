package com.yasir.erp.minierp.modules.receipt.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.receipt.domain.model.Receipt;

public interface ReceiptCommandPort {
    Receipt save(Receipt receipt);
    void deleteById(String id);
}