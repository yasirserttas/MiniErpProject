package com.yasir.erp.minierp.modules.bankAccount.application.exception;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String accountId, Object requestedAmount) {
        super("Insufficient balance for account ID: " + accountId + ", requested amount: " + requestedAmount);
    }
}
