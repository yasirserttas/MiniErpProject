package com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNoteStatus;

import java.time.LocalDateTime;
import java.util.Set;

public interface PromissoryNoteDueDateQueryPort {
    Set<PromissoryNote> findAllByDueDateBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
    Set<PromissoryNote> findAllByDueDateBetweenAndStatusAndActive
            (LocalDateTime start, LocalDateTime end, PromissoryNoteStatus status, boolean active);
}