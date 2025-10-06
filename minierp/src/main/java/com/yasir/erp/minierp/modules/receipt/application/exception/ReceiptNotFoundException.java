package com.yasir.erp.minierp.modules.receipt.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class ReceiptNotFoundException extends BusinessException {

    public ReceiptNotFoundException(String receiptId) {
        super("RECEIPT_NOT_FOUND" ,String.format("Receipt not found. ID[%s]",receiptId));
    }
}
