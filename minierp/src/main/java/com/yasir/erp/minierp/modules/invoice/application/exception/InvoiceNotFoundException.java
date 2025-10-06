package com.yasir.erp.minierp.modules.invoice.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class InvoiceNotFoundException extends BusinessException {

    public InvoiceNotFoundException(String invoiceId) {
        super("INVOICE_NOT_FOUND",String.format("invoice not found. ID[%s]",invoiceId));

    }}
