package com.yasir.erp.minierp.modules.bankMovement.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovement;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementStatus;

import java.time.LocalDateTime;
import java.util.Set;

public interface BankMovementDateQueryPort {
    Set<BankMovement> findAllByDateBetween(LocalDateTime start, LocalDateTime end);
    Set<BankMovement> findAllByDateBetweenAndStatus
            (LocalDateTime start, LocalDateTime end, BankMovementStatus status);
    Set<BankMovement> findAllByBankAccountIdAndDateBetween
            (String bankAccountId, LocalDateTime start, LocalDateTime end);
    Set<BankMovement> findAllByBankAccountIdAndDateBetweenAndStatus
            (String bankAccountId, LocalDateTime start, LocalDateTime end, BankMovementStatus status);
}
