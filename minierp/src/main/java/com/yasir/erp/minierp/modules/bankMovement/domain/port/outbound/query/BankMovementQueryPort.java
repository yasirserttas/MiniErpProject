package com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovement;

import java.util.Optional;

public interface BankMovementQueryPort {
    Optional<BankMovement> findById(String id);
}
