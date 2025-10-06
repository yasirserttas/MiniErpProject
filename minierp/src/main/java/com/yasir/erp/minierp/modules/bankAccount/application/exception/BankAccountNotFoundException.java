package com.yasir.erp.minierp.modules.bankAccount.application.exception;

import com.yasir.erp.minierp.common.exception.BusinessException;

public class BankAccountNotFoundException extends BusinessException {
    public BankAccountNotFoundException(String bankAccountId) {
        super("BANK_ACCOUNT_NOT_FOUND" ,String.format("BankAccount not found. ID[%s]",bankAccountId));
    }
}
