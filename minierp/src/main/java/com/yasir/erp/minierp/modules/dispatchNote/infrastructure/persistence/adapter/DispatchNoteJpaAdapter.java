package com.yasir.erp.minierp.modules.dispatchNote.infrastructure.persistence.adapter;

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNoteStatus;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.command.DispatchNoteCommandPort;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query.*;
import com.yasir.erp.minierp.modules.dispatchNote.infrastructure.persistence.DispatchNoteJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class DispatchNoteJpaAdapter implements
        DispatchNoteCommandPort,
        DispatchNoteQueryPort,
        DispatchNoteActiveQueryPort,
        DispatchNoteUserQueryPort,
        DispatchNoteCustomerQueryPort,
        DispatchNoteOrderQueryPort,
        DispatchNoteDateQueryPort,
        DispatchNoteStatusQueryPort,
        DispatchNoteNumberQueryPort {

    private final DispatchNoteJpaRepository repo;

    public DispatchNoteJpaAdapter(DispatchNoteJpaRepository repo) { this.repo = repo; }

    // command
    @Override public DispatchNote save(DispatchNote dn) { return repo.save(dn); }
    @Override public void delete(DispatchNote dn) { repo.delete(dn); }

    // generic
    @Override public Optional<DispatchNote> findById(String id) { return repo.findById(id); }

    // active
    @Override public Optional<DispatchNote> findByIdAndActive(String id, boolean active) {
        return repo.findByIdAndActive(id, active);
    }

    // user
    @Override public Optional<DispatchNote> findByUserIdAndActive(UUID userId, boolean active) {
        return repo.findByUserIdAndActive(userId, active);
    }
    @Override public Set<DispatchNote> findAllByUserIdAndActive(UUID userId, boolean active) {
        return repo.findAllByUserIdAndActive(userId, active);
    }

    // customer
    @Override public Optional<DispatchNote> findByCustomerIdAndActive(UUID customerId, boolean active) {
        return repo.findByCustomerIdAndActive(customerId, active);
    }
    @Override public Set<DispatchNote> findAllByCustomerIdAndActive(UUID customerId, boolean active) {
        return repo.findAllByCustomerIdAndActive(customerId, active);
    }

    // order
    @Override public Optional<DispatchNote> findByOrderIdAndActive(String orderId, boolean active) {
        return repo.findByOrderIdAndActive(orderId, active);
    }
    @Override public Set<DispatchNote> findAllByOrderIdAndActive(String orderId, boolean active) {
        return repo.findAllByOrderIdAndActive(orderId, active);
    }

    // date
    @Override public Set<DispatchNote> findAllByDispatchDateBetweenAndActive
    (LocalDateTime s, LocalDateTime e, boolean a) {
        return repo.findAllByDispatchDateBetweenAndActive(s, e, a);
    }

    // status
    @Override public Set<DispatchNote> findAllByDispatchNoteStatusAndActive
    (DispatchNoteStatus st, boolean a) {
        return repo.findAllByDispatchNoteStatusAndActive(st, a);
    }
    @Override public Set<DispatchNote> findAllByDispatchNoteStatusAndDispatchDateBetweenAndActive(
            DispatchNoteStatus st, LocalDateTime s, LocalDateTime e, boolean a) {
        return repo.findAllByDispatchNoteStatusAndDispatchDateBetweenAndActive(st, s, e, a);
    }

    // number
    @Override public boolean existsByDispatchNoteNumber(String number)
    { return repo.existsByDispatchNoteNumber(number); }
    @Override public Optional<DispatchNote> findByDispatchNoteNumberAndActive(String number, boolean active) {
        return repo.findByDispatchNoteNumberAndActive(number, active);
    }
}
