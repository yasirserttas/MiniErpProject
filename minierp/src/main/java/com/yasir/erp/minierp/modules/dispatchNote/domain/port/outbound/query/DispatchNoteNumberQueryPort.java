package com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;

import java.util.Optional;

public interface DispatchNoteNumberQueryPort {
    boolean existsByDispatchNoteNumber(String number);
    Optional<DispatchNote> findByDispatchNoteNumberAndActive(String number, boolean active);
}
