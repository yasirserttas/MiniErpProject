package com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;

import java.util.Optional;

public interface BankAccountIbanQueryPort {
    Optional<BankAccount> findByIban(String iban);
}
