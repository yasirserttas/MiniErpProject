package com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;

import java.util.Set;

public interface BankAccountBankNameQueryPort {
    Set<BankAccount> findAllByBankName(String bankName);
    Set<BankAccount> findAllByBankNameAndStatus(String bankName, BankAccountStatus status);
}
