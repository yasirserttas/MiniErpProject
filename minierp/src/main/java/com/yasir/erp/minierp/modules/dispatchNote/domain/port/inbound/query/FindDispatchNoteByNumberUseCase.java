package com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.query;

import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;

public interface FindDispatchNoteByNumberUseCase {
    DispatchNoteDto findDtoByDispatchNoteNumberAndActive(String number, boolean active);
}
