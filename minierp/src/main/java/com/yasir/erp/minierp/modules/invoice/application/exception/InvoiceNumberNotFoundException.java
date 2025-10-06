package com.yasir.erp.minierp.modules.invoice.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class InvoiceNumberNotFoundException extends BusinessException {

    public InvoiceNumberNotFoundException(String invoiceNumber) {
        super("INVOICE_NOT_FOUND", String.format("invoice not found. InvoiceNumber[%s]", invoiceNumber));
    }
}
