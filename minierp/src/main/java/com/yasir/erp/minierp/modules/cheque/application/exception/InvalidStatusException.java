package com.yasir.erp.minierp.modules.cheque.application.exception;

public class InvalidStatusException extends RuntimeException {

    public InvalidStatusException(String message) {
        super(message);
    }
}