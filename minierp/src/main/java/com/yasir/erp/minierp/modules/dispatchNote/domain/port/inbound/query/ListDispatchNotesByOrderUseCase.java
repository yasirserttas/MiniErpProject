package com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.query;
import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
import java.util.Set;

public interface ListDispatchNotesByOrderUseCase {
    Set<DispatchNoteDto> findDtoAllByOrderIdAndActive(String orderId, boolean active);
}
