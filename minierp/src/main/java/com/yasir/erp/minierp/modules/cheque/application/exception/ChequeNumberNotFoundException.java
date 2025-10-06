package com.yasir.erp.minierp.modules.cheque.application.exception;

public class ChequeNumberNotFoundException extends RuntimeException {

    public ChequeNumberNotFoundException(String chequeNumber) {
        super("Cheque with number '" + chequeNumber + "' was not found.");
    }
}