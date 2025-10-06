package com.yasir.erp.minierp.modules.cheque.application.converter;

import com.yasir.erp.minierp.modules.bankAccount.application.converter.BankAccountChequeConverter;
import com.yasir.erp.minierp.modules.cheque.application.dto.ChequeDto;
import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ChequeConverter {

    private final BankAccountChequeConverter bankAccountChequeConverter;

    public ChequeConverter(BankAccountChequeConverter bankAccountChequeConverter) {
        this.bankAccountChequeConverter = bankAccountChequeConverter;
    }

    public ChequeDto convertToChequeDto(Cheque cheque){

        return new ChequeDto(
                cheque.getId(),
                cheque.getChequeNumber(),
                cheque.getAmount(),
                cheque.getChequeNumber(),
                cheque.getIssueDate(),
                cheque.getDueDate(),
                cheque.getCreatedAt(),
                cheque.getUpdatedAt(),
                cheque.getActive(),
                cheque.getStatus(),
                bankAccountChequeConverter.convertToBankAccountCheque(cheque.getBankAccount())
        );
    }

    public Set<ChequeDto> convertToChequeSetDto(Set<Cheque> cheques){
        return cheques.stream().map(this::convertToChequeDto).collect(Collectors.toSet());
    }
}
