package com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;

import java.time.LocalDateTime;
import java.util.Set;

public interface DispatchNoteDateQueryPort {
    Set<DispatchNote> findAllByDispatchDateBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
}
