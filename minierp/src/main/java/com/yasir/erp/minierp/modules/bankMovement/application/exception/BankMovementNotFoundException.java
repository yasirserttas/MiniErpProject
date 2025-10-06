package com.yasir.erp.minierp.modules.bankMovement.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class BankMovementNotFoundException extends BusinessException {

    public BankMovementNotFoundException(String bankMovementId) {
        super("BANK_MOVEMENT_NOT_FOUND" ,String.format("BankMovement not found. ID[%s]",bankMovementId));
    }
}
