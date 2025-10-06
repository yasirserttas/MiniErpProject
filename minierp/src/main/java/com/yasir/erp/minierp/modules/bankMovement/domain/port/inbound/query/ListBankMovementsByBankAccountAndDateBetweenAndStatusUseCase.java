package com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.bankMovement.application.dto.BankMovementDto;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementStatus;

import java.time.LocalDateTime;
import java.util.Set;

public interface ListBankMovementsByBankAccountAndDateBetweenAndStatusUseCase {
    Set<BankMovementDto> listBankMovementsByBankAccountAndDateBetweenAndStatus
            (String bankAccountId, LocalDateTime start, LocalDateTime end, BankMovementStatus status);
}
