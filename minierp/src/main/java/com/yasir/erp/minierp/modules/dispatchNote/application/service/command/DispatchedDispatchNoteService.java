package com.yasir.erp.minierp.modules.dispatchNote.application.service.command;

import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import com.yasir.erp.minierp.modules.dispatchNote.application.exception.DispatchNoteAlreadyFinalizedException;
import com.yasir.erp.minierp.modules.dispatchNote.application.exception.DispatchNoteNotFoundException;
import com.yasir.erp.minierp.modules.dispatchNote.application.converter.DispatchNoteConverter;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNoteStatus;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.command.DispatchedDispatchNoteUseCase;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.command.DispatchNoteCommandPort;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query.DispatchNoteActiveQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DispatchedDispatchNoteService implements DispatchedDispatchNoteUseCase {

    private final DispatchNoteCommandPort commandPort;
    private final DispatchNoteActiveQueryPort activeQueryPort;
    private final DispatchNoteConverter converter;
    private final DispatchNotePdfService dispatchNotePdfService;

    public DispatchedDispatchNoteService(DispatchNoteCommandPort commandPort,
                                         DispatchNoteActiveQueryPort activeQueryPort,
                                         DispatchNoteConverter converter,
                                         DispatchNotePdfService dispatchNotePdfService) {
        this.commandPort = commandPort;
        this.activeQueryPort = activeQueryPort;
        this.converter = converter;
        this.dispatchNotePdfService = dispatchNotePdfService;
    }

    @Override
    public DispatchNoteDto dispatchedDispatchNote(String dispatchNoteId) {
        DispatchNote existing = activeQueryPort.findByIdAndActive(dispatchNoteId, true)
                .orElseThrow(() -> new DispatchNoteNotFoundException(dispatchNoteId));

        if (existing.getDispatchNoteStatus() == DispatchNoteStatus.DISPATCHED
                || existing.getDispatchNoteStatus() == DispatchNoteStatus.DELIVERED) {
            throw new DispatchNoteAlreadyFinalizedException(dispatchNoteId);
        }
        if (existing.getDispatchNoteStatus() != DispatchNoteStatus.PENDING) {
            throw new IllegalStateException
                    ("Yalnızca PENDING durumundaki irsaliye sevk edilebilir.");
        }

        DispatchNote updated = new DispatchNote(
                existing.getId(),
                existing.getUser(),
                existing.getCustomer(),
                existing.getOrder(),
                existing.getDeliveryAddress(),
                existing.getTransporterName(),
                existing.getTransporterPlate(),
                LocalDateTime.now(),
                existing.getEstimatedArrival(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                existing.getDispatchNoteNumber(),
                DispatchNoteStatus.DISPATCHED,
                existing.getDeliveredBy(),
                existing.getReceivedBy(),
                true
        );

        DispatchNote saved = commandPort.save(updated);
        DispatchNoteDto dto = converter.convertToDispatchNoteDto(saved);
        dispatchNotePdfService.generateDispatchNotePdf(dto);
        return dto;
    }
}
