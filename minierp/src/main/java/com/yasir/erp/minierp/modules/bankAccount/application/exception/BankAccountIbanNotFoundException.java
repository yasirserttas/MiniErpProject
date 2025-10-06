package com.yasir.erp.minierp.modules.bankAccount.application.exception;

public class BankAccountIbanNotFoundException extends RuntimeException {

    public BankAccountIbanNotFoundException(String iban) {
        super("No bank account found with IBAN: " + iban);
    }
}