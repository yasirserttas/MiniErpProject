package com.yasir.erp.minierp.modules.bankMovement.application.converter;

import com.yasir.erp.minierp.modules.bankAccount.application.converter.BankAccountBankMovementConverter;
import com.yasir.erp.minierp.modules.bankMovement.application.dto.BankMovementDto;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovement;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BankMovementConverter {

    private final BankAccountBankMovementConverter bankAccountBankMovementConverter;

    public BankMovementConverter(BankAccountBankMovementConverter bankAccountBankMovementConverter) {
        this.bankAccountBankMovementConverter = bankAccountBankMovementConverter;
    }

    public BankMovementDto convertToBankMovementDto(BankMovement bankMovement){

        return new BankMovementDto(
                bankMovement.getId(),
                bankMovement.getAmount(),
                bankMovement.getType(),
                bankMovement.getBankMovementStatus(),
                bankAccountBankMovementConverter.
                        convertToBankAccountBankMovementDto(bankMovement.getBankAccount()),
                bankMovement.getDescription(),
                bankMovement.getCreatedAt(),
                bankMovement.getUpdatedAt(),
                bankMovement.getDate()
        );
    }

    public Set<BankMovementDto> convertToBankMovementSetDto(Set<BankMovement> bankMovements){
        return bankMovements.stream().map(this::convertToBankMovementDto).collect(Collectors.toSet());
    }
}
