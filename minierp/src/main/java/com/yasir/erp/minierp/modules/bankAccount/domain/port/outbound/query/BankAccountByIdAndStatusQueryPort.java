package com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;

import java.util.Optional;

public interface BankAccountByIdAndStatusQueryPort {
    Optional<BankAccount> findByIdAndStatus(String id, BankAccountStatus status);
}
