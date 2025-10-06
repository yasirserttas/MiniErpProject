package com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovement;

public interface BankMovementCommandPort {
    BankMovement save(BankMovement m);
    void delete(BankMovement m);
}
