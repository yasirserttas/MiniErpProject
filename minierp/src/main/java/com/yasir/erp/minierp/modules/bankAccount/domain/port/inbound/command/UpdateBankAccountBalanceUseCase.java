package com.yasir.erp.minierp.modules.bankAccount.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementType;

import java.math.BigDecimal;

public interface UpdateBankAccountBalanceUseCase {
    BankAccountDto updateBalance(String bankAccountId, BigDecimal amount, BankMovementType type);
}
