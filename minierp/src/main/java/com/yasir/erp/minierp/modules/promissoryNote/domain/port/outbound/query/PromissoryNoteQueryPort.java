package com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;

import java.util.Optional;

public interface PromissoryNoteQueryPort {
    Optional<PromissoryNote> findByIdAndActive(String id, boolean active);
    Optional<PromissoryNote> findByNoteNumberAndActive(String noteNumber, boolean active);
}
