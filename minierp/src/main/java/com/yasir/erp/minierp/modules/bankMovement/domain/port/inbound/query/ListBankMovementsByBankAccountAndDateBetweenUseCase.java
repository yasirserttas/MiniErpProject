package com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.bankMovement.application.dto.BankMovementDto;

import java.time.LocalDateTime;
import java.util.Set;

public interface ListBankMovementsByBankAccountAndDateBetweenUseCase {
    Set<BankMovementDto> listBankMovementsByBankAccountAndDateBetween
            (String bankAccountId, LocalDateTime start, LocalDateTime end);
}
