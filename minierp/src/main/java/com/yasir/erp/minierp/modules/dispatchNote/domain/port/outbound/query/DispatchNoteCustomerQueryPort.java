package com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface DispatchNoteCustomerQueryPort {
    Optional<DispatchNote> findByCustomerIdAndActive(UUID customerId, boolean active);
    Set<DispatchNote> findAllByCustomerIdAndActive(UUID customerId, boolean active);
}
