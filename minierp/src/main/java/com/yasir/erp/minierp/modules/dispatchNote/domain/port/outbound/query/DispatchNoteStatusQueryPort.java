package com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNoteStatus;

import java.time.LocalDateTime;
import java.util.Set;

public interface DispatchNoteStatusQueryPort {
    Set<DispatchNote> findAllByDispatchNoteStatusAndActive
            (DispatchNoteStatus status, boolean active);
    Set<DispatchNote> findAllByDispatchNoteStatusAndDispatchDateBetweenAndActive(
            DispatchNoteStatus status, LocalDateTime start, LocalDateTime end, boolean active);
}
