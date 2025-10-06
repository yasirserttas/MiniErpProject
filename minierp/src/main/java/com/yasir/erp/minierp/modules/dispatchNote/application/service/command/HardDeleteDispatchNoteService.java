package com.yasir.erp.minierp.modules.dispatchNote.application.service.command;

import com.yasir.erp.minierp.modules.dispatchNote.application.exception.DispatchNoteNotFoundException;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.inbound.command.HardDeleteDispatchNoteUseCase;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.command.DispatchNoteCommandPort;
import com.yasir.erp.minierp.modules.dispatchNote.domain.port.outbound.query.DispatchNoteActiveQueryPort;
import com.yasir.erp.minierp.modules.dispatchNote.domain.model.DispatchNote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HardDeleteDispatchNoteService implements HardDeleteDispatchNoteUseCase {

    private final DispatchNoteCommandPort commandPort;
    private final DispatchNoteActiveQueryPort activeQueryPort;

    public HardDeleteDispatchNoteService(DispatchNoteCommandPort commandPort,
                                         DispatchNoteActiveQueryPort activeQueryPort) {
        this.commandPort = commandPort;
        this.activeQueryPort = activeQueryPort;
    }

    @Override
    public void hardDeleteById(String dispatchNoteId) {
        DispatchNote dispatchNote = activeQueryPort.findByIdAndActive(dispatchNoteId, false)
                .orElseThrow(() -> new DispatchNoteNotFoundException(dispatchNoteId));
        commandPort.delete(dispatchNote);
    }
}
