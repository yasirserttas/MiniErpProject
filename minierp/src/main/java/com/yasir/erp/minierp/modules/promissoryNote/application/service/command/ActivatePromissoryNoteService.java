package com.yasir.erp.minierp.modules.promissoryNote.application.service.command;

import com.yasir.erp.minierp.modules.promissoryNote.application.exception.PromissoryNoteNotFoundException;
import com.yasir.erp.minierp.modules.promissoryNote.application.converter.PromissoryNoteConverter;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.command.ActivatePromissoryNoteUseCase;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.command.PromissoryNoteCommandPort;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query.PromissoryNoteQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ActivatePromissoryNoteService implements ActivatePromissoryNoteUseCase {

    private final PromissoryNoteCommandPort commandPort;
    private final PromissoryNoteQueryPort noteQueryPort;
    private final PromissoryNoteConverter promissoryNoteConverter;

    public ActivatePromissoryNoteService(PromissoryNoteCommandPort commandPort,
                                         PromissoryNoteQueryPort noteQueryPort,
                                         PromissoryNoteConverter promissoryNoteConverter) {
        this.commandPort = commandPort;
        this.noteQueryPort = noteQueryPort;
        this.promissoryNoteConverter = promissoryNoteConverter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PromissoryNoteDto activatePromissoryNote(String id) {

        PromissoryNote existing =
                noteQueryPort.findByIdAndActive(id, false)
                        .orElseThrow(() -> new PromissoryNoteNotFoundException(id));

        PromissoryNote activated = new PromissoryNote(
                existing.getId(), existing.getNoteNumber(),
                existing.getAmount(), existing.getDebtor(),
                existing.getIssueDate(), existing.getDueDate(),
                existing.getCreatedAt(), LocalDateTime.now(),
                true, existing.getStatus(), existing.getBankAccount()
        );

       return promissoryNoteConverter.convertToPromissoryNoteDto(commandPort.save(activated));
    }
}
