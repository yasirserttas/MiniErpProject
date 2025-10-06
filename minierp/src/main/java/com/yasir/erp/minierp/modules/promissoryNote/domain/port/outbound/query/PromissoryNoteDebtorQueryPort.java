package com.yasir.erp.minierp.modules.promissoryNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNote;

import java.util.Set;

public interface PromissoryNoteDebtorQueryPort {
    Set<PromissoryNote> findAllByDebtorAndActive(String debtor, boolean active);
}