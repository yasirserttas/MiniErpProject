package com.yasir.erp.minierp.modules.supplier.application.service.command;

import com.yasir.erp.minierp.modules.supplier.application.exception.SupplierNotFoundException;
import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import com.yasir.erp.minierp.modules.supplier.domain.port.inbound.command.HardDeleteSupplierByIdUseCase;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.command.SupplierCommandPort;
import com.yasir.erp.minierp.modules.supplier.domain.port.outbound.query.SupplierByIdAndActiveQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HardDeleteSupplierByIdService implements HardDeleteSupplierByIdUseCase {

    private final SupplierCommandPort commandPort;
    private final SupplierByIdAndActiveQueryPort byIdAndActivePort;

    public HardDeleteSupplierByIdService(SupplierCommandPort commandPort,
                                         SupplierByIdAndActiveQueryPort byIdAndActivePort) {
        this.commandPort = commandPort;
        this.byIdAndActivePort = byIdAndActivePort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void hardDeleteSupplierById(String supplierId) {

        Supplier existing = byIdAndActivePort.findByIdAndActive(supplierId, false)
                .orElseThrow(() -> new SupplierNotFoundException(supplierId));

        commandPort.delete(existing);
    }
}
