package com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;
import com.yasir.erp.minierp.modules.bankAccount.application.dto.request.UpdateBankAccountDtoRequest;

public interface UpdateBankAccountUseCase {
    BankAccountDto update(UpdateBankAccountDtoRequest updateBankAccountDtoRequest);
}
