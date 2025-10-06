package com.yasir.erp.minierp.modules.cheque.application.exception;

public class AlreadyPaidException extends RuntimeException {

    public AlreadyPaidException(String documentType, String documentId) {
        super(documentType + " with ID '" + documentId + "' is already marked as PAID.");
    }
}
