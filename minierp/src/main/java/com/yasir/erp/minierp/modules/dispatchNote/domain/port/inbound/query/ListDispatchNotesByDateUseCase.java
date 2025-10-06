package com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.query;
import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import java.time.LocalDateTime; import java.util.Set;

public interface ListDispatchNotesByDateUseCase {
    Set<DispatchNoteDto> findDtoAllByDispatchDateBetweenAndActive
            (LocalDateTime start, LocalDateTime end, boolean active);
}