package com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNoteStatus;

import java.util.Set;

public interface PromissoryNoteStatusQueryPort {
    Set<PromissoryNote> findAllByStatusAndActive(PromissoryNoteStatus status, boolean active);
}