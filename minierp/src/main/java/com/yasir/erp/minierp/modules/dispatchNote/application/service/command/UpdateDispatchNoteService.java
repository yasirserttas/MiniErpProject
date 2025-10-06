package com.yasir.erp.minierp.modules.dispatchNote.application.service.command;

import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import com.yasir.erp.minierp.dto.dispatchNote.request.UpdateDispatchNoteDtoRequest;
import com.yasir.erp.minierp.modules.dispatchNote.application.exception.DispatchNoteNotFoundException;
import com.yasir.erp.minierp.modules.dispatchNote.application.converter.DispatchNoteConverter;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.command.UpdateDispatchNoteUseCase;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.command.DispatchNoteCommandPort;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query.DispatchNoteActiveQueryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateDispatchNoteService implements UpdateDispatchNoteUseCase {

    private final DispatchNoteConverter dispatchNoteConverter;
    private final DispatchNoteCommandPort dispatchNoteCommandPort;
    private final DispatchNoteActiveQueryPort dispatchNoteActiveQueryPort;
    private final DispatchNotePdfService dispatchNotePdfService;

    public UpdateDispatchNoteService(DispatchNoteConverter dispatchNoteConverter,
                                     DispatchNoteCommandPort dispatchNoteCommandPort,
                                     DispatchNoteActiveQueryPort dispatchNoteActiveQueryPort,
                                     DispatchNotePdfService dispatchNotePdfService) {
        this.dispatchNoteConverter = dispatchNoteConverter;
        this.dispatchNoteCommandPort = dispatchNoteCommandPort;
        this.dispatchNoteActiveQueryPort = dispatchNoteActiveQueryPort;
        this.dispatchNotePdfService = dispatchNotePdfService;
    }

    @Override
    public DispatchNoteDto updateDispatchNote
            (UpdateDispatchNoteDtoRequest updateDispatchNoteDtoRequest) {
        DispatchNote existing =
                dispatchNoteActiveQueryPort.
                        findByIdAndActive
                                (updateDispatchNoteDtoRequest.getDispatchNoteId(),true).
                        orElseThrow(()-> new
                                DispatchNoteNotFoundException
                                (updateDispatchNoteDtoRequest.getDispatchNoteId()));




        DispatchNote update = new DispatchNote(
                existing.getId(),
                existing.getUser(),
                existing.getCustomer(),
                existing.getOrder(),
                existing.getOrder().getShippingAddress(),
                updateDispatchNoteDtoRequest.getTransporterName(),
                updateDispatchNoteDtoRequest.getTransporterPlate(),
                existing.getDispatchDate(),
                updateDispatchNoteDtoRequest.getEstimatedArrival(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                updateDispatchNoteDtoRequest.getDispatchNoteNumber(),
                existing.getDispatchNoteStatus(),
                updateDispatchNoteDtoRequest.getDeliveredBy(),
                updateDispatchNoteDtoRequest.getReceivedBy(),
                true
        );

        DispatchNote saved = dispatchNoteCommandPort.save(update);
        DispatchNoteDto dto = dispatchNoteConverter.convertToDispatchNoteDto(saved);
        dispatchNotePdfService.generateDispatchNotePdf(dto);
        return dto;

    }
}