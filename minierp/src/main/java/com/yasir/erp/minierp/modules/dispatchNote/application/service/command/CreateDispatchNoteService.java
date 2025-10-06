package com.yasir.erp.minierp.modules.dispatchNote.application.service.command;

import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import com.yasir.erp.minierp.dto.dispatchNote.request.CreateDispatchNoteDtoRequest;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.customer.application.exception.CustomerNotFoundException;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.query.CustomerActiveQueryPort;
import com.yasir.erp.minierp.modules.dispatchNote.application.converter.DispatchNoteConverter;
import com.yasir.erp.minierp.modules.dispatchNote.application.exception.DispatchNoteNumberAlreadyExistsException;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNoteStatus;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.command.CreateDispatchNoteUseCase;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.command.DispatchNoteCommandPort;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query.DispatchNoteNumberQueryPort;
import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import com.yasir.erp.minierp.modules.user.application.exception.UserNotFoundException;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.UserByIdQueryPort;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.time.LocalDateTime;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CreateDispatchNoteService implements CreateDispatchNoteUseCase {

    private final DispatchNoteCommandPort dispatchNoteCommandPort;
    private final DispatchNoteNumberQueryPort dispatchNoteNumberQueryPort;
    private final DispatchNoteConverter dispatchNoteConverter;
    private final UserByIdQueryPort userByIdQueryPort;
    private final CustomerActiveQueryPort customerActiveQueryPort;
    private final OrderActiveQueryPort orderActiveQueryPort;
    private final DispatchNotePdfService dispatchNotePdfService;

    public CreateDispatchNoteService(DispatchNoteCommandPort dispatchNoteCommandPort,
                                     DispatchNoteNumberQueryPort dispatchNoteNumberQueryPort,
                                     DispatchNoteConverter dispatchNoteConverter,
                                     UserByIdQueryPort userByIdQueryPort,
                                     CustomerActiveQueryPort customerActiveQueryPort,
                                     OrderActiveQueryPort orderActiveQueryPort,
                                     DispatchNotePdfService dispatchNotePdfService) {
        this.dispatchNoteCommandPort = dispatchNoteCommandPort;
        this.dispatchNoteNumberQueryPort = dispatchNoteNumberQueryPort;
        this.dispatchNoteConverter = dispatchNoteConverter;
        this.userByIdQueryPort = userByIdQueryPort;
        this.customerActiveQueryPort = customerActiveQueryPort;
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.dispatchNotePdfService = dispatchNotePdfService;
    }

    @Override
    public DispatchNoteDto createDispatchNote
            (CreateDispatchNoteDtoRequest createDispatchNoteDtoRequest) {


        if (dispatchNoteNumberQueryPort.
                existsByDispatchNoteNumber
                        (createDispatchNoteDtoRequest.getDispatchNoteNumber())) {
            throw new DispatchNoteNumberAlreadyExistsException
                    (createDispatchNoteDtoRequest.getDispatchNoteNumber());
        }

        User user = userByIdQueryPort.findById(createDispatchNoteDtoRequest.getUserId())
                .orElseThrow(() -> new
                        UserNotFoundException(createDispatchNoteDtoRequest.getUserId()));

        Customer customer = customerActiveQueryPort.
                findByIdAndActive(createDispatchNoteDtoRequest.getCustomerId(), true)
                .orElseThrow(() -> new
                        CustomerNotFoundException(createDispatchNoteDtoRequest.getCustomerId()));

        Order order = orderActiveQueryPort.
                findByIdAndActive(createDispatchNoteDtoRequest.getOrderId(), true)
                .orElseThrow(() -> new
                        OrderNotFoundException(createDispatchNoteDtoRequest.getOrderId()));

        DispatchNote dispatchNote = new DispatchNote(
                UlidGenerator.generate(),
                user,
                customer,
                order,
                order.getShippingAddress(),
                createDispatchNoteDtoRequest.getTransporterName(),
                createDispatchNoteDtoRequest.getTransporterPlate(),
                LocalDateTime.now(),
                createDispatchNoteDtoRequest.getEstimatedArrival(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                createDispatchNoteDtoRequest.getDispatchNoteNumber(),
                DispatchNoteStatus.PENDING,
                createDispatchNoteDtoRequest.getDeliveredBy(),
                createDispatchNoteDtoRequest.getReceivedBy(),
                true
        );

        DispatchNote saved = dispatchNoteCommandPort.save(dispatchNote);
        DispatchNoteDto dto = dispatchNoteConverter.convertToDispatchNoteDto(saved);
        dispatchNotePdfService.generateDispatchNotePdf(dto);
        return dto;
    }
}
