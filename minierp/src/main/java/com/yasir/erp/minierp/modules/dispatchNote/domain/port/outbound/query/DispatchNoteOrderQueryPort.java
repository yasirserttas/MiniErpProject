package com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;

import java.util.Optional;
import java.util.Set;

public interface DispatchNoteOrderQueryPort {
    Optional<DispatchNote> findByOrderIdAndActive(String orderId, boolean active);
    Set<DispatchNote> findAllByOrderIdAndActive(String orderId, boolean active);
}
