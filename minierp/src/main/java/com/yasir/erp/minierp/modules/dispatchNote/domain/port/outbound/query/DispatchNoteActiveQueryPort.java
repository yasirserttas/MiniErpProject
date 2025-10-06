package com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;

import java.util.Optional;

public interface DispatchNoteActiveQueryPort {
    Optional<DispatchNote> findByIdAndActive(String id, boolean active);
}
