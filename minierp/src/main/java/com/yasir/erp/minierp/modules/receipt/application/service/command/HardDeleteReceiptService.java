package com.yasir.erp.minierp.modules.receipt.application.service.command;

import com.yasir.erp.minierp.modules.receipt.domain.port.inbound.command.HardDeleteReceiptUseCase;
import com.yasir.erp.minierp.modules.receipt.domain.port.outbound.command.ReceiptCommandPort;
import com.yasir.erp.minierp.modules.receipt.domain.port.outbound.query.ReceiptQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HardDeleteReceiptService implements HardDeleteReceiptUseCase {

    private final ReceiptCommandPort commandPort;

    public HardDeleteReceiptService(ReceiptCommandPort commandPort, ReceiptQueryPort queryPort) {
        this.commandPort = commandPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void hardDeleteReceipt(String id) {
        commandPort.deleteById(id);
    }
}
