package com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.command;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;

public interface DeactivatePromissoryNoteUseCase {
    PromissoryNoteDto deactivatePromissoryNote(String promissoryNoteId);
}