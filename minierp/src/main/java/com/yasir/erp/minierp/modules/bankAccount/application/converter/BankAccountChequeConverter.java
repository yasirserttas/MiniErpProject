package com.yasir.erp.minierp.modules.bankAccount.application.converter;


import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountChequeDto;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BankAccountChequeConverter {

    public BankAccountChequeDto convertToBankAccountCheque(BankAccount bankAccount){

        return new BankAccountChequeDto  (
                bankAccount.getId(),
                bankAccount.getBankName(),
                bankAccount.getIban(),
                bankAccount.getStatus()
        );
    }
    public Set<BankAccountChequeDto> convertToSetBankAccountCheque(Set<BankAccount> bankAccounts){

        return bankAccounts.stream().
                map(this::convertToBankAccountCheque).collect(Collectors.toSet());
    }
}