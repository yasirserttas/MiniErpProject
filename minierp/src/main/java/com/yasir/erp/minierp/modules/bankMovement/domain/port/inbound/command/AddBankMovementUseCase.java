package com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.bankMovement.application.dto.BankMovementDto;
import com.yasir.erp.minierp.modules.bankMovement.application.dto.request.CreateBankMovementDtoRequest;

public interface AddBankMovementUseCase {
    BankMovementDto addBankMovement(CreateBankMovementDtoRequest createBankMovementDtoRequest);
}
