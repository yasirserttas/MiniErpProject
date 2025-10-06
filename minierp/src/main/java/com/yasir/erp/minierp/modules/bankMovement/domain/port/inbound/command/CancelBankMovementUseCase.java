package com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.command;

public interface CancelBankMovementUseCase {
    void cancel(String movementId);
}
