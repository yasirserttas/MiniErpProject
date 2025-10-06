package com.yasir.erp.minierp.modules.cheque.application.service.command;

import com.yasir.erp.minierp.modules.cheque.domain.model.Cheque;
import com.yasir.erp.minierp.modules.cheque.domain.port.inbound.command.HardDeleteChequeUseCase;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.command.ChequeCommandPort;
import com.yasir.erp.minierp.modules.cheque.domain.port.outbound.query.ChequeQueryPort;
import com.yasir.erp.minierp.modules.cheque.application.exception.ChequeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HardDeleteChequeService implements HardDeleteChequeUseCase {

    private final ChequeCommandPort chequeCommandPort;
    private final ChequeQueryPort chequeQueryPort;

    public HardDeleteChequeService(ChequeCommandPort chequeCommandPort, ChequeQueryPort chequeQueryPort) {
        this.chequeCommandPort = chequeCommandPort;
        this.chequeQueryPort = chequeQueryPort;
    }

    @Override
    public void hardDelete(String chequeId) {
        Cheque existing = chequeQueryPort
                .findByIdAndActive(chequeId, true)
                .orElseThrow(() -> new ChequeNotFoundException(chequeId));

        chequeCommandPort.delete(existing);
    }
}
