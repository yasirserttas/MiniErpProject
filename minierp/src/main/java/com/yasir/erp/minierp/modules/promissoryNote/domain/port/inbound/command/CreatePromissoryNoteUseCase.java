package com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.request.CreatePromissoryNoteDtoRequest;

public interface CreatePromissoryNoteUseCase {
    PromissoryNoteDto addPromissoryNote(CreatePromissoryNoteDtoRequest request);
}