package com.yasir.erp.minierp.modules.promissoryNote.application.service.command;

import com.yasir.erp.minierp.modules.promissoryNote.application.exception.PromissoryNoteNotFoundException;
import com.yasir.erp.minierp.modules.promissoryNote.application.converter.PromissoryNoteConverter;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNoteStatus;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.command.CancelPromissoryNoteUseCase;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.command.PromissoryNoteCommandPort;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query.PromissoryNoteQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CancelPromissoryNoteService implements CancelPromissoryNoteUseCase {

    private final PromissoryNoteCommandPort commandPort;
    private final PromissoryNoteQueryPort noteQueryPort;
    private final PromissoryNoteConverter converter;

    public CancelPromissoryNoteService(PromissoryNoteCommandPort commandPort,
                                       PromissoryNoteQueryPort noteQueryPort,
                                       PromissoryNoteConverter converter) {
        this.commandPort = commandPort;
        this.noteQueryPort = noteQueryPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PromissoryNoteDto cancelPromissoryNote(String id) {

        PromissoryNote existing = noteQueryPort.findByIdAndActive(id, true)
                .orElseThrow(()-> new PromissoryNoteNotFoundException(id));

        if (existing.getStatus() == PromissoryNoteStatus.PAID) {
            throw new IllegalStateException("Paid promissory note cannot be cancelled.");
        }

        PromissoryNote cancelled = new PromissoryNote(
                existing.getId(),
                existing.getNoteNumber(),
                existing.getAmount(),
                existing.getDebtor(),
                existing.getIssueDate(),
                existing.getDueDate(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                true,
                PromissoryNoteStatus.CANCELLED,
                existing.getBankAccount()
        );

        return converter.convertToPromissoryNoteDto(commandPort.save(cancelled));
    }
}