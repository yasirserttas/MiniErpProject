package com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.command;

import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;

public interface ActivateDispatchNoteUseCase {
    DispatchNoteDto activeDispatchNote(String id);
}