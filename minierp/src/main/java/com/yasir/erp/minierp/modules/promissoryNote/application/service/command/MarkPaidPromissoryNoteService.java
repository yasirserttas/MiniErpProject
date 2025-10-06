package com.yasir.erp.minierp.modules.promissoryNote.application.service.command;

import com.yasir.erp.minierp.modules.promissoryNote.application.exception.PromissoryNoteNotFoundException;
import com.yasir.erp.minierp.modules.bankMovement.application.dto.request.CreateBankMovementDtoRequest;
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementType;
import com.yasir.erp.minierp.modules.bankMovement.domain.port.inbound.command.AddBankMovementUseCase;
import com.yasir.erp.minierp.modules.promissoryNote.application.converter.PromissoryNoteConverter;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNoteStatus;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.command.MarkPaidPromissoryNoteUseCase;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.command.PromissoryNoteCommandPort;
import com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query.PromissoryNoteQueryPort;
import com.yasir.erp.minierp.modules.cheque.application.exception.AlreadyPaidException;
import com.yasir.erp.minierp.modules.cheque.application.exception.InactiveEntityException;
import com.yasir.erp.minierp.modules.cheque.application.exception.InvalidStatusException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MarkPaidPromissoryNoteService implements MarkPaidPromissoryNoteUseCase {

    private final PromissoryNoteCommandPort commandPort;
    private final PromissoryNoteQueryPort noteQueryPort;
    private final AddBankMovementUseCase addBankMovement;
    private final PromissoryNoteConverter converter;

    public MarkPaidPromissoryNoteService(PromissoryNoteCommandPort commandPort,
                                         PromissoryNoteQueryPort noteQueryPort,
                                         AddBankMovementUseCase addBankMovement,
                                         PromissoryNoteConverter converter) {
        this.commandPort = commandPort;
        this.noteQueryPort = noteQueryPort;
        this.addBankMovement = addBankMovement;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PromissoryNoteDto markPromissoryNoteAsPaid(String id) {

        PromissoryNote existing =
                noteQueryPort.findByIdAndActive(id, true)
                        .orElseThrow(()-> new PromissoryNoteNotFoundException(id));

        if (!existing.getActive())
            throw new InactiveEntityException("PromissoryNote", id);

        if (existing.getStatus() == PromissoryNoteStatus.PAID)
            throw new AlreadyPaidException("PromissoryNote", id);

        if (existing.getStatus() != PromissoryNoteStatus.OPEN)
            throw new InvalidStatusException("PromissoryNote must be in OPEN status to be paid.");

        addBankMovement.addBankMovement(new CreateBankMovementDtoRequest(
                existing.getBankAccount().getId(),
                existing.getAmount(),
                BankMovementType.OUTCOME,
                "Paid promissory note: " + id
        ));

        PromissoryNote paid = new PromissoryNote(
                existing.getId(),
                existing.getNoteNumber(),
                existing.getAmount(),
                existing.getDebtor(),
                existing.getIssueDate(),
                existing.getDueDate(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                true,
                PromissoryNoteStatus.PAID,
                existing.getBankAccount()
        );

        return converter.convertToPromissoryNoteDto(commandPort.save(paid));
    }
}
