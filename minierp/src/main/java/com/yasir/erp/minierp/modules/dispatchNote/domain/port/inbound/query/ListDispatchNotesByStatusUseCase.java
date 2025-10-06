package com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNoteStatus;
import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import java.time.LocalDateTime;
import java.util.Set;

public interface ListDispatchNotesByStatusUseCase {
    Set<DispatchNoteDto> findDtoAllByDispatchNoteStatusAndActive
            (DispatchNoteStatus status, boolean active);
    Set<DispatchNoteDto> findDtoAllByDispatchNoteStatusAndDispatchDateBetweenAndActive
            (DispatchNoteStatus status, LocalDateTime start, LocalDateTime end, boolean active);
}