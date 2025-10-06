package com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import com.yasir.erp.minierp.modules.promissoryNote.application.dto.request.UpdatePromissoryNoteDtoRequest;

public interface UpdatePromissoryNoteUseCase {
    PromissoryNoteDto updatePromissoryNote(UpdatePromissoryNoteDtoRequest request);
}