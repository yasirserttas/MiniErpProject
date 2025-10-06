package com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;

import java.time.LocalDateTime;
import java.util.Set;

public interface BankAccountCreatedAtQueryPort {
    Set<BankAccount> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    Set<BankAccount> findAllByStatusAndCreatedAtBetween(BankAccountStatus status,
                                                        LocalDateTime start,
                                                        LocalDateTime end);
}
