package com.yasir.erp.minierp.modules.bankMovement.application.exception;

public class AlreadyCancelledBankMovementException extends RuntimeException {

    public AlreadyCancelledBankMovementException(String movementId) {
        super("Bank movement with ID " + movementId + " is already cancelled.");
    }
}
