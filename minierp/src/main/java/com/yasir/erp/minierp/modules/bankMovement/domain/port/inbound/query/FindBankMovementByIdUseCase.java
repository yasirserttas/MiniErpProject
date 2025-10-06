package com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.bankMovement.application.dto.BankMovementDto;

public interface FindBankMovementByIdUseCase {
    BankMovementDto findBankMovementById(String id);
}
