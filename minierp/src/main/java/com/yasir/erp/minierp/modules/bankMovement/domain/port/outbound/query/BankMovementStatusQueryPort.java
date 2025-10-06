package com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovement;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementStatus;

import java.util.Set;

public interface BankMovementStatusQueryPort {
    Set<BankMovement> findAllByStatus(BankMovementStatus status);
}
