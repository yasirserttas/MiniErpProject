package com.yasir.erp.minierp.modules.cheque.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class ChequeNotFoundException extends BusinessException {

    public ChequeNotFoundException(String chequeId) {
        super("CHEQUE_NOT_FOUND" ,String.format("Cheque not found. ID[%s]",chequeId));
    }
}
