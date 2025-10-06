package com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.command;

import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;

public interface PromissoryNoteCommandPort {
    PromissoryNote save(PromissoryNote note);
}
