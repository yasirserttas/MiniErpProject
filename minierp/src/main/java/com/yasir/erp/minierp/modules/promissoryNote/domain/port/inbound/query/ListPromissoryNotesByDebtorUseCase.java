package com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import java.util.Set;

public interface ListPromissoryNotesByDebtorUseCase {
    Set<PromissoryNoteDto> findDtoAllByDebtorAndActive(String debtor, boolean active);
}