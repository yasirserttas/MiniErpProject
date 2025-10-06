package com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;

import java.math.BigDecimal;
import java.util.Set;

public interface PromissoryNoteAmountQueryPort {
    Set<PromissoryNote> findAllByAmountBetweenAndActive
            (BigDecimal min, BigDecimal max, boolean active);
}