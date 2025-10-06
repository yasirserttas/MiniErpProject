package com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListPromissoryNotesByUpdatedAtBetweenUseCase {
    Set<PromissoryNoteDto> findDtoAllByUpdatedAtBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
}