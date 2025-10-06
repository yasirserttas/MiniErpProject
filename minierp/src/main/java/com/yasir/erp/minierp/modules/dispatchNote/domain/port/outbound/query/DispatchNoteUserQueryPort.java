package com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface DispatchNoteUserQueryPort {
    Optional<DispatchNote> findByUserIdAndActive(UUID userId, boolean active);
    Set<DispatchNote> findAllByUserIdAndActive(UUID userId, boolean active);
}
