package com.yasir.erp.minierp.modules.bankAccount.application.exception;

public class DuplicateBankAccountIbanException extends RuntimeException {

    public DuplicateBankAccountIbanException(String iban) {
        super("A bank account with IBAN '" + iban + "' already exists.");
    }
}