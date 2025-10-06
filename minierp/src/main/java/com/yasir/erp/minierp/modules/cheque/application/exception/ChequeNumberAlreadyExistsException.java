package com.yasir.erp.minierp.modules.cheque.application.exception;

public class ChequeNumberAlreadyExistsException extends RuntimeException{

    public ChequeNumberAlreadyExistsException(String chequeNumber) {
        super("Cheque with number '" + chequeNumber + "' already exists.");
    }
}
