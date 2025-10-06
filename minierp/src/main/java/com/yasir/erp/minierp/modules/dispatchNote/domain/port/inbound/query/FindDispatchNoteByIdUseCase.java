package com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.query;
import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;

public interface FindDispatchNoteByIdUseCase {
    DispatchNoteDto findDtoByIdAndActive(String id, boolean active);
}