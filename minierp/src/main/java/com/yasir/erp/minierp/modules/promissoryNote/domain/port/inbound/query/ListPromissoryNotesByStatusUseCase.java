package com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNoteStatus;
import java.util.Set;

public interface ListPromissoryNotesByStatusUseCase {
    Set<PromissoryNoteDto> findDtoAllByStatusAndActive(PromissoryNoteStatus status, boolean active);
}