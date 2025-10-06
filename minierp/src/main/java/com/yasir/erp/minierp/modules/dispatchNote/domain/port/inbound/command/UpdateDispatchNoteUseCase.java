package com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.command;
import com.yasir.erp.minierp.dto.dispatchNote.request.UpdateDispatchNoteDtoRequest;
import com.yasir.erp.minierp.dto.dispatchNote.DispatchNoteDto;
public interface UpdateDispatchNoteUseCase {
    DispatchNoteDto updateDispatchNote(UpdateDispatchNoteDtoRequest updateDispatchNoteDtoRequest);
}