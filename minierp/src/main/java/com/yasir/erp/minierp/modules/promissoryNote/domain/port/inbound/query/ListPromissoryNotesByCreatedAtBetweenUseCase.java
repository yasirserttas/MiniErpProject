package com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListPromissoryNotesByCreatedAtBetweenUseCase {
    Set<PromissoryNoteDto> findDtoAllByCreatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
}