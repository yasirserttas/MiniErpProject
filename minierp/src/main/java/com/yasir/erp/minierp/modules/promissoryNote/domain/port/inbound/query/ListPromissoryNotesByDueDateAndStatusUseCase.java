package com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNoteStatus;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListPromissoryNotesByDueDateAndStatusUseCase {
    Set<PromissoryNoteDto> findDtoAllByDueDateBetweenAndStatusAndActive
            (LocalDateTime start, LocalDateTime end, PromissoryNoteStatus status, boolean active);
}