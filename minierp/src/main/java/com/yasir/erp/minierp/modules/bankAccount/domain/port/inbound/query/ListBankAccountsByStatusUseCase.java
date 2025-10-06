package com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;

import java.util.Set;

public interface ListBankAccountsByStatusUseCase {
    Set<BankAccountDto> list(BankAccountStatus status);
}
