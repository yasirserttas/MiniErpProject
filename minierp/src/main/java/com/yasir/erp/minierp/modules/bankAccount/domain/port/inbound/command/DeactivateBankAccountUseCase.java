package com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;

public interface DeactivateBankAccountUseCase {
    BankAccountDto deactivate(String accountId);
}
