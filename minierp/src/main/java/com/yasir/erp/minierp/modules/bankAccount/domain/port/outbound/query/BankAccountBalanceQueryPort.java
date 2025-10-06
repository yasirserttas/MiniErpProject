package com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;

import java.math.BigDecimal;
import java.util.Set;

public interface BankAccountBalanceQueryPort {
    Set<BankAccount> findAllByBalanceBetween(BigDecimal min, BigDecimal max);
}
