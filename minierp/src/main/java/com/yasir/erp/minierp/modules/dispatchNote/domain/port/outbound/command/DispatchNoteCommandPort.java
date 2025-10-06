package com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;

public interface DispatchNoteCommandPort {
    DispatchNote save(DispatchNote dispatchNote);
    void delete(DispatchNote dispatchNote);
}
