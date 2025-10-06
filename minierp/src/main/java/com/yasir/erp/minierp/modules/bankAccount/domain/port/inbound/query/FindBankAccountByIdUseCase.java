package com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;

public interface FindBankAccountByIdUseCase {
    BankAccountDto find(String id, BankAccountStatus status);
}
