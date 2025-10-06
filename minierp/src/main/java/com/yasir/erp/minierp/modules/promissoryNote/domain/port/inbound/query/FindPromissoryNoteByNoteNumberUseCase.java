package com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;

public interface FindPromissoryNoteByNoteNumberUseCase {
    PromissoryNoteDto findDtoByNoteNumberAndActive(String noteNumber, boolean active);
}