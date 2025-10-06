package com.yasir.erp.minierp.modules.bankAccount.application.converter;

import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountPromissoryDto;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BankAccountPromissoryConverter {

    public BankAccountPromissoryDto convertToBankAccountPromissoryDto(BankAccount bankAccount){

        return new  BankAccountPromissoryDto(
                bankAccount.getId(),
                bankAccount.getBankName(),
                bankAccount.getIban(),
                bankAccount.getCurrency(),
                bankAccount.getStatus()
        );
    }

    public Set<BankAccountPromissoryDto> convertToSetBankAccountPromissoryDto
            (Set<BankAccount> bankAccounts) {

      return bankAccounts.stream().map(this::convertToBankAccountPromissoryDto).collect(Collectors.toSet());
    }

}
