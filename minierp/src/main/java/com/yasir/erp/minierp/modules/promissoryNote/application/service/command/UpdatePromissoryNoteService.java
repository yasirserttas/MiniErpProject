package com.yasir.erp.minierp.modules.promissoryNote.application.service.command;

import com.yasir.erp.minierp.modules.bankAccount.application.exception.BankAccountNotFoundException;
import com.yasir.erp.minierp.modules.promissoryNote.application.exception.PromissoryNoteNotFoundException;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountQueryPort;
import com.yasir.erp.minierp.modules.promissoryNote.application.converter.PromissoryNoteConverter;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.request.UpdatePromissoryNoteDtoRequest;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.command.UpdatePromissoryNoteUseCase;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.command.PromissoryNoteCommandPort;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query.PromissoryNoteQueryPort;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdatePromissoryNoteService implements UpdatePromissoryNoteUseCase {

    private final PromissoryNoteCommandPort commandPort;
    private final PromissoryNoteQueryPort noteQueryPort;
    private final BankAccountQueryPort bankAccountQueryPort;
    private final PromissoryNoteConverter converter;

    public UpdatePromissoryNoteService(PromissoryNoteCommandPort commandPort,
                                       PromissoryNoteQueryPort noteQueryPort,
                                       BankAccountQueryPort bankAccountQueryPort,
                                       PromissoryNoteConverter converter) {
        this.commandPort = commandPort;
        this.noteQueryPort = noteQueryPort;
        this.bankAccountQueryPort = bankAccountQueryPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PromissoryNoteDto updatePromissoryNote
            (UpdatePromissoryNoteDtoRequest updatePromissoryNoteDtoRequest) {

        PromissoryNote existing = noteQueryPort.
                findByIdAndActive(updatePromissoryNoteDtoRequest.getId(), true)
                .orElseThrow(()->
                        new PromissoryNoteNotFoundException(
                                updatePromissoryNoteDtoRequest.getId()));

        BankAccount bankAccount = bankAccountQueryPort.findByIdAndStatus
                (existing.getBankAccount().getId(), BankAccountStatus.ACTIVE)
                .orElseThrow(()-> new BankAccountNotFoundException
                        (existing.getBankAccount().getId()));


        PromissoryNote updated = new PromissoryNote(
                existing.getId(),
                updatePromissoryNoteDtoRequest.getNoteNumber(),
                updatePromissoryNoteDtoRequest.getAmount(),
                updatePromissoryNoteDtoRequest.getDebtor(),
                existing.getIssueDate(),
                updatePromissoryNoteDtoRequest.getDueDate(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                true,
                existing.getStatus(),
                bankAccount
        );

        return converter.convertToPromissoryNoteDto(commandPort.save(updated));
    }
}
