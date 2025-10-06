package com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;

import java.time.LocalDateTime;
import java.util.Set;

public interface PromissoryNoteCreatedAtQueryPort {
    Set<PromissoryNote> findAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
}