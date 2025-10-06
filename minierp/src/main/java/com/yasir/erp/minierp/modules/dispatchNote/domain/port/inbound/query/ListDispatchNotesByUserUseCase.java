package com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.query;
import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import java.util.Set;
import java.util.UUID;
public interface ListDispatchNotesByUserUseCase {
    Set<DispatchNoteDto> findDtoAllByUserIdAndActive(UUID userId, boolean active);
}