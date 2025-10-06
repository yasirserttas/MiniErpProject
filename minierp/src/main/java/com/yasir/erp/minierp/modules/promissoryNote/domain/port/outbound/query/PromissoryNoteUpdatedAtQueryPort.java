package com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;

import java.time.LocalDateTime;
import java.util.Set;

public interface PromissoryNoteUpdatedAtQueryPort {
    Set<PromissoryNote> findAllByUpdatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
}