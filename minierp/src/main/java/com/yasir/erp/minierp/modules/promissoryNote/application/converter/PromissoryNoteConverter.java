package com.yasir.erp.minierp.modules.promissoryNote.application.converter;

import com.yasir.erp.minierp.modules.bankAccount.application.converter.BankAccountPromissoryConverter;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PromissoryNoteConverter {

    private final BankAccountPromissoryConverter bankAccountPromissoryConverter;

    public PromissoryNoteConverter(BankAccountPromissoryConverter bankAccountPromissoryConverter) {
        this.bankAccountPromissoryConverter = bankAccountPromissoryConverter;
    }

    public PromissoryNoteDto convertToPromissoryNoteDto(PromissoryNote promissoryNote){

        return new PromissoryNoteDto(
                promissoryNote.getId(),
                promissoryNote.getNoteNumber(),
                promissoryNote.getAmount(),
                promissoryNote.getDebtor(),
                promissoryNote.getIssueDate(),
                promissoryNote.getDueDate(),
                promissoryNote.getCreatedAt(),
                promissoryNote.getUpdatedAt(),
                promissoryNote.getActive(),
                promissoryNote.getStatus(),
                bankAccountPromissoryConverter.
                        convertToBankAccountPromissoryDto(promissoryNote.getBankAccount())
        );

    }

    public Set<PromissoryNoteDto> convertToPromissoryNoteSetDto(Set<PromissoryNote> promissoryNotes){
        return promissoryNotes.stream().map(this::convertToPromissoryNoteDto).collect(Collectors.toSet());
    }
}
