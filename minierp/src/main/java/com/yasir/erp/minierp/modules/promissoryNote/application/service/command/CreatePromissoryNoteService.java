package com.yasir.erp.minierp.modules.promissoryNote.application.service.command;

import com.yasir.erp.minierp.modules.bankAccount.application.exception.BankAccountNotFoundException;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount;
import com.yasir.erp.minierp.modules.bankAccount.domain.port.outbound.query.BankAccountQueryPort;
import com.yasir.erp.minierp.modules.promissoryNote.application.converter.PromissoryNoteConverter;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.request.CreatePromissoryNoteDtoRequest;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNoteStatus;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.command.CreatePromissoryNoteUseCase;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.command.PromissoryNoteCommandPort;
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreatePromissoryNoteService implements CreatePromissoryNoteUseCase {

    private final PromissoryNoteCommandPort commandPort;
    private final BankAccountQueryPort bankAccountQueryPort;
    private final PromissoryNoteConverter converter;

    public CreatePromissoryNoteService(PromissoryNoteCommandPort commandPort,
                                       BankAccountQueryPort bankAccountQueryPort,
                                       PromissoryNoteConverter converter) {
        this.commandPort = commandPort;
        this.bankAccountQueryPort = bankAccountQueryPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PromissoryNoteDto addPromissoryNote
            (CreatePromissoryNoteDtoRequest createPromissoryNoteDtoRequest) {

        BankAccount bankAccount = bankAccountQueryPort
                .findByIdAndStatus
                        (createPromissoryNoteDtoRequest.getBankAccountId(),
                                BankAccountStatus.ACTIVE).
                orElseThrow(()->new BankAccountNotFoundException
                        (createPromissoryNoteDtoRequest.getBankAccountId()));

        PromissoryNote note = new PromissoryNote(
                UlidGenerator.generate(),
                createPromissoryNoteDtoRequest.getNoteNumber(),
                createPromissoryNoteDtoRequest.getAmount(),
                createPromissoryNoteDtoRequest.getDebtor(),
                createPromissoryNoteDtoRequest.getIssueDate(),
                createPromissoryNoteDtoRequest.getDueDate(),
                LocalDateTime.now(),
                null,
                true,
                PromissoryNoteStatus.OPEN,
                bankAccount
        );

        return converter.convertToPromissoryNoteDto(commandPort.save(note));
    }
}
