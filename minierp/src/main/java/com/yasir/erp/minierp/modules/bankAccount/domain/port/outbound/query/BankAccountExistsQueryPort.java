package com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query;

public interface BankAccountExistsQueryPort {
    boolean existsByAccountNumber(String accountNumber);
}
