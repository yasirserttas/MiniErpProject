package com.yasir.erp.minierp.modules.cheque.application.exception;

public class InactiveEntityException extends RuntimeException {

    public InactiveEntityException(String entityName, String entityId) {
        super(entityName + " with ID '" + entityId + "' is inactive and cannot be processed.");
    }
}
