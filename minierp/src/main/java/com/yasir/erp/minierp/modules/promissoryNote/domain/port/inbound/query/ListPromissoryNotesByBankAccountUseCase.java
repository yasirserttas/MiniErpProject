package com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import java.util.Set;

public interface ListPromissoryNotesByBankAccountUseCase {
    Set<PromissoryNoteDto> findDtoAllByBankAccountIdAndActive(String bankAccountId, boolean active);
}