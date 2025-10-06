package com.yasir.erp.minierp.modules.invoice.application.service.command;

import com.yasir.erp.minierp.modules.invoice.domain.port.inbound.command.HardDeleteInvoiceUseCase;
import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query.InvoiceActiveQueryPort;
import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.command.InvoiceCommandPort;
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;
import com.yasir.erp.minierp.modules.invoice.application.exception.InvoiceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HardDeleteInvoiceService implements HardDeleteInvoiceUseCase {

    private final InvoiceCommandPort commandPort;
    private final InvoiceActiveQueryPort activeQueryPort;

    public HardDeleteInvoiceService(InvoiceCommandPort commandPort,
                                    InvoiceActiveQueryPort activeQueryPort) {
        this.commandPort = commandPort;
        this.activeQueryPort = activeQueryPort;
    }

    @Override
    public void hardDelete(String invoiceId) {
        Invoice inv = activeQueryPort.findByIdAndActive(invoiceId, false)
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));
        commandPort.delete(inv);
    }
}
