package com.yasir.erp.minierp.modules.dispatchNote.application.service.query;

import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import com.yasir.erp.minierp.modules.dispatchNote.application.converter.DispatchNoteConverter;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNoteStatus;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.query.*;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.dispatchNote.application.exception.DispatchNoteNotFoundException;
import com.yasir.erp.minierp.modules.dispatchNote.application.exception.DispatchNoteNumberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class DispatchNoteQueryService implements
        FindDispatchNoteByIdUseCase,
        FindDispatchNoteByNumberUseCase,
        ListDispatchNotesByCustomerUseCase,
        ListDispatchNotesByDateUseCase,
        ListDispatchNotesByOrderUseCase,
        ListDispatchNotesByStatusUseCase,
        ListDispatchNotesByUserUseCase{

    private final DispatchNoteConverter converter;

    private final DispatchNoteActiveQueryPort activeQueryPort;
    private final DispatchNoteNumberQueryPort numberQueryPort;
    private final DispatchNoteUserQueryPort userQueryPort;
    private final DispatchNoteOrderQueryPort orderQueryPort;
    private final DispatchNoteCustomerQueryPort customerQueryPort;
    private final DispatchNoteDateQueryPort dateQueryPort;
    private final DispatchNoteStatusQueryPort statusQueryPort;


    public DispatchNoteQueryService(DispatchNoteConverter converter,
                                    DispatchNoteActiveQueryPort activeQueryPort,
                                    DispatchNoteNumberQueryPort numberQueryPort,
                                    DispatchNoteUserQueryPort userQueryPort,
                                    DispatchNoteOrderQueryPort orderQueryPort,
                                    DispatchNoteCustomerQueryPort customerQueryPort,
                                    DispatchNoteDateQueryPort dateQueryPort,
                                    DispatchNoteStatusQueryPort statusQueryPort
    ) {
        this.converter = converter;
        this.activeQueryPort = activeQueryPort;
        this.numberQueryPort = numberQueryPort;
        this.userQueryPort = userQueryPort;
        this.orderQueryPort = orderQueryPort;
        this.customerQueryPort = customerQueryPort;
        this.dateQueryPort = dateQueryPort;
        this.statusQueryPort = statusQueryPort;
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public DispatchNoteDto findDtoByIdAndActive(String id, boolean active) {
        return converter.convertToDispatchNoteDto(
                findByIdAndActive(id, active)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public DispatchNoteDto findDtoByDispatchNoteNumberAndActive(String number, boolean active) {
        return converter.convertToDispatchNoteDto(
                findByDispatchNoteNumberAndActive(number, active)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<DispatchNoteDto> findDtoAllByCustomerIdAndActive(UUID customerId, boolean active) {
        return converter.convertToDispatchNoteSetDto(
                findAllByCustomerIdAndActive(customerId, active)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<DispatchNoteDto> findDtoAllByDispatchDateBetweenAndActive(LocalDateTime start, LocalDateTime end, boolean active) {
        return converter.convertToDispatchNoteSetDto(
                findAllByDispatchDateBetweenAndActive(start, end, active)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<DispatchNoteDto> findDtoAllByOrderIdAndActive(String orderId, boolean active) {
        return converter.convertToDispatchNoteSetDto(
                findAllByOrderIdAndActive(orderId, active)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<DispatchNoteDto> findDtoAllByDispatchNoteStatusAndActive(DispatchNoteStatus status, boolean active) {
        return converter.convertToDispatchNoteSetDto(
                findAllByDispatchNoteStatusAndActive(status, active)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<DispatchNoteDto> findDtoAllByDispatchNoteStatusAndDispatchDateBetweenAndActive(DispatchNoteStatus status, LocalDateTime start, LocalDateTime end, boolean active) {
        return converter.convertToDispatchNoteSetDto(
                findAllByDispatchNoteStatusAndDispatchDateBetweenAndActive(status, start, end, active)
        );
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<DispatchNoteDto> findDtoAllByUserIdAndActive(UUID userId, boolean active) {
        return converter.convertToDispatchNoteSetDto(
                findAllByUserIdAndActive(userId, active)
        );
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected DispatchNote findByIdAndActive(String id, boolean active) {
        return activeQueryPort.findByIdAndActive(id, active)
                .orElseThrow(() -> new DispatchNoteNotFoundException(id));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected DispatchNote findByDispatchNoteNumberAndActive(String number, boolean active) {
        return numberQueryPort.findByDispatchNoteNumberAndActive(number, active)
                .orElseThrow(() -> new DispatchNoteNumberNotFoundException(number));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<DispatchNote> findAllByCustomerIdAndActive(UUID customerId, boolean active) {
        return customerQueryPort.findAllByCustomerIdAndActive(customerId, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<DispatchNote> findAllByDispatchDateBetweenAndActive(LocalDateTime start, LocalDateTime end, boolean active) {
        return dateQueryPort.findAllByDispatchDateBetweenAndActive(start, end, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<DispatchNote> findAllByOrderIdAndActive(String orderId, boolean active) {
        return orderQueryPort.findAllByOrderIdAndActive(orderId, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<DispatchNote> findAllByDispatchNoteStatusAndActive(DispatchNoteStatus status, boolean active) {
        return statusQueryPort.findAllByDispatchNoteStatusAndActive(status, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<DispatchNote> findAllByDispatchNoteStatusAndDispatchDateBetweenAndActive(DispatchNoteStatus status, LocalDateTime start, LocalDateTime end, boolean active) {
        return statusQueryPort.findAllByDispatchNoteStatusAndDispatchDateBetweenAndActive(status, start, end, active);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    protected Set<DispatchNote> findAllByUserIdAndActive(UUID userId, boolean active) {
        return userQueryPort.findAllByUserIdAndActive(userId, active);
    }
}