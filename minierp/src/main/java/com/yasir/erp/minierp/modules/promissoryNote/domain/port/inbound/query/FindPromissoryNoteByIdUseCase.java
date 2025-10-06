package com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;

public interface FindPromissoryNoteByIdUseCase {
    PromissoryNoteDto findDtoByIdAndActive(String promissoryNoteId, boolean active);
}