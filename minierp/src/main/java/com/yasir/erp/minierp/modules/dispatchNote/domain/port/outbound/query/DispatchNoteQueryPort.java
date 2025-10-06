package com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;

import java.util.Optional;

public interface DispatchNoteQueryPort {
    Optional<DispatchNote> findById(String id);
}
