package com.yasir.erp.minierp.modules.promissoryNote.domain.port.inbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.application.dto.PromissoryNoteDto;
import java.math.BigDecimal;
import java.util.Set;

public interface ListPromissoryNotesByAmountBetweenUseCase {
    Set<PromissoryNoteDto> findDtoAllByAmountBetweenAndActive
            (BigDecimal minAmount, BigDecimal maxAmount, boolean active);
}