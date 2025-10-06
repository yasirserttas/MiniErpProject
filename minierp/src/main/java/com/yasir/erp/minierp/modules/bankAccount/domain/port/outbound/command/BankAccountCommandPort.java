package com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;

public interface BankAccountCommandPort {
    BankAccount save(BankAccount bankAccount);
    void delete(BankAccount bankAccount);
}
