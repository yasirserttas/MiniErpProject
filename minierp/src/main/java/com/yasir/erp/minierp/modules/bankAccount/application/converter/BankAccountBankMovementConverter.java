package com.yasir.erp.minierp.modules.bankAccount.application.converter;

import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountBankMovementDto;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BankAccountBankMovementConverter {
    public BankAccountBankMovementDto convertToBankAccountBankMovementDto(BankAccount bankAccount) {
        if (bankAccount == null) {
            return null;
        }
        return new BankAccountBankMovementDto(
                bankAccount.getId(),
                bankAccount.getBankName(),
                bankAccount.getIban(),
                bankAccount.getStatus()
        );
    }

    public Set<BankAccountBankMovementDto> convertToSetBankAccountBankMovementDto
            (Set<BankAccount> bankAccounts){
        return bankAccounts.stream().
                map(this::convertToBankAccountBankMovementDto).collect(Collectors.toSet());
    }
}
