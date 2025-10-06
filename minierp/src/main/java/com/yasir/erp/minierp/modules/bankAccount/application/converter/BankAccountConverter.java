package com.yasir.erp.minierp.modules.bankAccount.application.converter;

import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountDto;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BankAccountConverter {

    public BankAccountDto convertToBankAccountDto (BankAccount bankAccount){

        return new BankAccountDto(
                bankAccount.getId(),
                bankAccount.getBankName(),
                bankAccount.getAccountNumber(),
                bankAccount.getIban(),
                bankAccount.getCurrency(),
                bankAccount.getBalance(),
                bankAccount.getCreatedAt(),
                bankAccount.getUpdatedAt(),
                bankAccount.getStatus()
        );
    }

    public Set<BankAccountDto> convertToBankAccountSetDto (Set<BankAccount> bankAccounts){
        return bankAccounts.stream().map(this::convertToBankAccountDto).collect(Collectors.toSet());
    }
}
