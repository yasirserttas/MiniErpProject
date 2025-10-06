package com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.bankMovement.application.dto.BankMovementDto;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementStatus;

import java.util.Set;

public interface ListBankMovementsByBankAccountAndStatusUseCase {
    Set<BankMovementDto> listBankMovementsByBankAccountAndStatus
            (String bankAccountId, BankMovementStatus status);
}
