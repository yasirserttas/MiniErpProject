package com.yasir.erp.minierp.modules.bankAccount.application.exception;

public class DuplicateBankAccountNumberException extends RuntimeException {

    public DuplicateBankAccountNumberException(String accountNumber) {
        super("A bank account with account number '" + accountNumber + "' already exists.");
    }
}
