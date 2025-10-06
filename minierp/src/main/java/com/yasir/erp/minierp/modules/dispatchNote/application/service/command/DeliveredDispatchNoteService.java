package com.yasir.erp.minierp.modules.dispatchNote.application.service.command;

import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import com.yasir.erp.minierp.modules.dispatchNote.application.exception.DispatchNoteAlreadyFinalizedException;
import com.yasir.erp.minierp.modules.dispatchNote.application.exception.DispatchNoteNotFoundException;
import com.yasir.erp.minierp.modules.dispatchNote.application.converter.DispatchNoteConverter;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNoteStatus;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound
        .command.DeliveredDispatchNoteUseCase;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound
        .command.DispatchNoteCommandPort;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound
        .query.DispatchNoteActiveQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DeliveredDispatchNoteService implements DeliveredDispatchNoteUseCase {

    private final DispatchNoteCommandPort commandPort;
    private final DispatchNoteActiveQueryPort activeQueryPort;
    private final DispatchNoteConverter converter;
    private final DispatchNotePdfService pdfService;

    public DeliveredDispatchNoteService(DispatchNoteCommandPort commandPort,
                                        DispatchNoteActiveQueryPort activeQueryPort,
                                        DispatchNoteConverter converter,
                                        DispatchNotePdfService pdfService) {
        this.commandPort = commandPort;
        this.activeQueryPort = activeQueryPort;
        this.converter = converter;
        this.pdfService = pdfService;
    }

    @Override
    public DispatchNoteDto deliveredDispatchNote(String dispatchNoteId) {
        DispatchNote existing = activeQueryPort.findByIdAndActive(dispatchNoteId, true)
                .orElseThrow(() -> new DispatchNoteNotFoundException(dispatchNoteId));

        if (existing.getDispatchNoteStatus() == DispatchNoteStatus.DELIVERED) {
            throw new DispatchNoteAlreadyFinalizedException(dispatchNoteId);
        }
        if (existing.getDispatchNoteStatus() != DispatchNoteStatus.DISPATCHED) {
            throw new IllegalStateException
                    ("Yalnızca DISPATCHED durumundaki irsaliye teslim edilebilir.");
        }

        DispatchNote updated = new DispatchNote(
                existing.getId(),
                existing.getUser(),
                existing.getCustomer(),
                existing.getOrder(),
                existing.getDeliveryAddress(),
                existing.getTransporterName(),
                existing.getTransporterPlate(),
                existing.getDispatchDate(),
                existing.getEstimatedArrival(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                existing.getDispatchNoteNumber(),
                DispatchNoteStatus.DELIVERED,
                existing.getDeliveredBy(),
                existing.getReceivedBy(),
                true
        );

        DispatchNote saved = commandPort.save(updated);
        DispatchNoteDto dto = converter.convertToDispatchNoteDto(saved);
        pdfService.generateDispatchNotePdf(dto);
        return dto;
    }
}
